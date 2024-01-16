package com.example.wineycommon.service;


import static com.example.wineycommon.constants.SlackStatic.*;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SlackService {
	@Value("${slack.webhook-url}")
	private String webhookUrl;

	@Value("${spring.config.activate.on-profile}")
	private String profile;


	@Async("slack-notification")
	public void sendMessage(String user, Exception exception, HttpServletRequest request){
		final Slack slack = Slack.getInstance();
		final String message = SlackConverter.errorToSlackMessage(user, request, exception, profile);
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
}
