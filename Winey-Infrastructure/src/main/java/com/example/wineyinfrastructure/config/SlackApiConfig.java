package com.example.wineyinfrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;

@Configuration
public class SlackApiConfig {

	@Value("${slack.token}")
	private String token;

	@Bean
	public MethodsClient getClient() {
		Slack slackClient = Slack.getInstance();
		return slackClient.methods(token);
	}
}
