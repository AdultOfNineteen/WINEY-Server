package com.example.wineycommon.service;

import static com.example.wineycommon.constants.SlackStatic.*;

import javax.servlet.http.HttpServletRequest;

import com.slack.api.webhook.Payload;

public class SlackConverter {

	public static String errorToSlackMessage(String user, HttpServletRequest request, Exception exception, String profile) {
		StringBuilder sb = new StringBuilder();
		sb.append(CODE_BLOCK_START);
		appendLabelAndValue(sb, USER_LABEL, user);
		appendLabelAndValue(sb, PROFILE_LABEL, profile);
		appendLabelAndValue(sb, SERVER_LABEL, request.getServerName());
		appendLabelAndValue(sb, URI_LABEL, request.getRequestURI());
		appendLabelAndValue(sb, METHOD_LABEL, request.getMethod());
		appendLabelAndValue(sb, QUERY_STRING_LABEL, request.getQueryString());
		appendLabelAndValue(sb, EXCEPTION_CLASS_LABEL, getErrorOccurredClassName(exception));
		appendLabelAndValue(sb, REMOTE_ADDR_LABEL, request.getRemoteAddr());
		appendLabelAndValue(sb, REMOTE_HOST_LABEL, request.getRemoteHost());
		appendLabelAndValue(sb, REMOTE_PORT_LABEL, Integer.toString(request.getRemotePort()));
		appendLabelAndValue(sb, SERVER_PORT_LABEL, Integer.toString(request.getServerPort()));
		appendLabelAndValue(sb, SERVLET_PATH_LABEL, request.getServletPath());
		appendLabelAndValue(sb, EXCEPTION_LABEL, exception.getMessage());
		sb.append(CODE_BLOCK_END);
		return sb.toString();
	}

	private static void appendLabelAndValue(StringBuilder sb, String label, String value) {
		sb.append(label).append(value).append("\n");
	}

	private static String getErrorOccurredClassName(Exception exception) {
		StackTraceElement[] stackTrace = exception.getStackTrace();
		if (stackTrace.length > 0) {
			StackTraceElement firstStackTraceElement = stackTrace[0];
			return String.format(EXCEPTION_CLASS_MESSAGE_VALUE, firstStackTraceElement.getClassName(), firstStackTraceElement.getLineNumber());
		}
		return UNKNOWN_EXCEPTION_CLASS_VALUE;
	}

	public static Payload convertToPayload(String message) {
		return Payload
			.builder()
			.text(message)
			.username(SLACK_USER_NAME)
			.iconUrl(SLACK_IMG_URL)
			.build();
	}
}
