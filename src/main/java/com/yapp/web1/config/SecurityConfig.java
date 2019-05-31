package com.yapp.web1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

/**
 * SecurityConfig
 *
 * @author Dakyung Ko
 */
@Configuration
@EnableWebSecurity//(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Filter ssoFilter;
    private final Environment env;

    public SecurityConfig(Filter ssoFilter, Environment env) {
        this.ssoFilter = ssoFilter;
        this.env = env;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        if(isTestMode()) {
            web.ignoring()
                    .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**");
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if(isTestMode()) {
            setTestMode(http);
        } else {
            setRealMode(http);
        }
    }

    private boolean isTestMode() {
        String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "test";
        return profile.equals("test");
    }

    private void setTestMode(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                    .antMatchers("/", "/me", "/h2/**", "/h2-console/**", "/api/login*/**", "/api/logout*/**", "/api/_hcheck", "/auth", "/session",
                        "/js/**", "/css/**", "/image/**", "/fonts/**",
                        "/favicon.ico", "/static/**", "/**/*.json", "/**/*.html", "/**/*.js")
                        .permitAll()
                    .mvcMatchers(HttpMethod.GET, "/api/order*/**", "/api/project/**")
                        .permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                    .and().exceptionHandling()
                      .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/api/login"))
                    .and().headers().frameOptions().disable()
                    .and().csrf().disable()
                    .addFilterBefore(ssoFilter, BasicAuthenticationFilter.class)
        ;
        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .deleteCookies("SESSION")
                .permitAll()
        ;
    }

    private void setRealMode(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests().antMatchers("/", "/api/login**", "/api/_hcheck").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/order*/**", "/api/project/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().headers().frameOptions().sameOrigin() //
                .and().csrf().disable() // TODO CSRF 설정
                .addFilterBefore(ssoFilter, BasicAuthenticationFilter.class);
        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
    }

}
