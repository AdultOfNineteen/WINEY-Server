package com.example.wineyinfrastructure.slack.converter;


import static com.example.wineyinfrastructure.slack.constants.SlackStatic.*;

import javax.servlet.http.HttpServletRequest;

import com.slack.api.webhook.Payload;

public class SlackConverter {

	public static String errorToSlackMessage(String user, HttpServletRequest request, Exception exception, String profile,
		String url, String method, String errorMessage, String errorStack, String errorUserIP) {
		StringBuilder sb = new StringBuilder();
		sb.append(CODE_BLOCK_START);
		appendLabelAndValue(sb, USER_LABEL, user);
		appendLabelAndValue(sb, PROFILE_LABEL, profile);
		appendLabelAndValue(sb, SERVER_LABEL, url);
		appendLabelAndValue(sb, METHOD_LABEL, method);
		appendLabelAndValue(sb, QUERY_STRING_LABEL, request.getQueryString());
		appendLabelAndValue(sb, EXCEPTION_CLASS_LABEL, getErrorOccurredClassName(exception));
		appendLabelAndValue(sb, EXCEPTION_LABEL, errorMessage);
		appendLabelAndValue(sb, EXCEPTION_MESSAGE_LABEL, errorStack);
		appendLabelAndValue(sb, IP_LABEL, errorUserIP);
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
