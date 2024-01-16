package com.example.wineyinfrastructure.slack.service;


import static com.example.wineyinfrastructure.slack.constants.SlackStatic.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.example.wineyinfrastructure.slack.converter.SlackConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackService {
	@Value("${slack.webhook-url}")
	private String webhookUrl;
	@Value("${spring.config.activate.on-profile}")
	private String profile;
	private final ObjectMapper objectMapper;


	@Async("slack-notification")
	public void sendMessage(String user, Exception exception, HttpServletRequest cachingRequest) throws
		IOException {
		final String url = cachingRequest.getRequestURL().toString();
		final String method = cachingRequest.getMethod();
		final String errorMessage = exception.getMessage();
		String errorStack = getErrorStack(exception);
		final String errorUserIP = cachingRequest.getRemoteAddr();

		final Slack slack = Slack.getInstance();
		final String message = SlackConverter.errorToSlackMessage(user, cachingRequest, exception, profile, url, method, errorMessage, errorStack, errorUserIP);
		final Payload payload = SlackConverter.convertToPayload(message);
		try {
			String responseBody = slack.send(webhookUrl, payload).getBody();
			if (!StringUtils.equals(responseBody, "ok")) {
				throw new UnknownHostException(UNKNOWN_HOST_EXCEPTION_MESSAGE);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private String getErrorStack(Throwable throwable) {
		String exceptionAsString = Arrays.toString(throwable.getStackTrace());
		int cutLength = Math.min(exceptionAsString.length(), MAX_LEN);
		return exceptionAsString.substring(0, cutLength);
	}
}
