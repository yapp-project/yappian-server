package com.yapp.web1.config;

import com.yapp.web1.social.GoogleOAuth2ClientAuthenticationProcessingFilter;
import com.yapp.web1.social.SocialService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * OAuthConfig
 *
 * @author Dakyung Ko
 */
@Configuration
@EnableOAuth2Client
public class OAuthConfig {

    private final OAuth2ClientContext oAuth2ClientContext;

    private final SocialService socialService;

    public OAuthConfig(@Qualifier("oauth2ClientContext") OAuth2ClientContext oAuth2ClientContext, SocialService socialService) {
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.socialService = socialService;
    }

    @Bean
    @ConfigurationProperties("google")
    public ClientResources google() {
        return new ClientResources();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    public Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(google(), new GoogleOAuth2ClientAuthenticationProcessingFilter(socialService)));
        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, OAuth2ClientAuthenticationProcessingFilter filter) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(client.getClient(), oAuth2ClientContext);
        filter.setRestTemplate(restTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
        filter.setTokenServices(tokenServices);
        tokenServices.setRestTemplate(restTemplate);
        return filter;
    }
}
