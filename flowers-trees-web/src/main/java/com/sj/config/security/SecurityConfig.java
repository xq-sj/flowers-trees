package com.sj.config.security;

import com.sj.config.filter.JwtAuthenticationFilter;
import com.sj.config.handler.LoginFailHandler;
import com.sj.config.handler.LoginSuccessHandler;
import com.sj.config.handler.MyAccessDeniedHandler;
import com.sj.config.handler.MyAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @ClassName SecurityConfig
 * @Author 闪光灯
 * @Date 2023/7/27 15:45
 * @Description SpringSecurity配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginFailHandler loginFailHandler() {
        return new LoginFailHandler();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }


    @Bean
    public MyAuthenticationEntryPoint myAuthenticationEntryPoint() {
        return new MyAuthenticationEntryPoint();
    }

    @Bean
    public MyAccessDeniedHandler myAccessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Resource
    public UserDetailServiceImpl userDetailService;

    @Resource
    public JwtAuthenticationFilter jwtAuthenticationFilter;

    private String[] URL_WHITE_LIST = {"/login/**", "/logout/**", "/user/register", "/favicon.ico", "/user/test", "/user/code","/user/forget","/user/login"};

    private String[] RESOURCE_WHITE_LIST = {"/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 权限设置
                .authorizeRequests(authorize -> authorize
                        .antMatchers(URL_WHITE_LIST).permitAll()
                        .antMatchers(RESOURCE_WHITE_LIST).permitAll()
                        .anyRequest().authenticated())
                // 登录设置
                .formLogin(conf -> conf
                        // 登录成功处理
                        .successHandler(loginSuccessHandler())
                        // 登录失败处理
                        .failureHandler(loginFailHandler()))
                // 处理认证失败、鉴权失败
                .exceptionHandling(conf -> conf.authenticationEntryPoint(myAuthenticationEntryPoint()).accessDeniedHandler(myAccessDeniedHandler()))
                // 基于token，不需要csrf
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 跨域配置
                .cors(conf -> conf.configurationSource(corsConfigurationSource()))
                // 不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 添加过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(daoAuthenticationProvider())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }


}
