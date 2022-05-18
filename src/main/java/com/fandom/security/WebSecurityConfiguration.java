package com.fandom.security;

import com.fandom.model.Role;
import com.fandom.services.UserSecurityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserSecurityServices userSecurityServices;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfiguration(UserSecurityServices userSecurityServices, BCryptPasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userSecurityServices = userSecurityServices;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());

        http.authorizeRequests().antMatchers("/", "/createAccount", "/login", "/fandom/**").permitAll();

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/post/**",
                                            "/interaction/**", "/comment/**",
                                            "/user/basic/**", "/schedule/**",  "/profile/**", "/message/**").permitAll();

        http.authorizeRequests().antMatchers("/admin").hasAuthority(Role.ADMIN.name());
        http.authorizeRequests().antMatchers("/user").hasAnyAuthority(Role.MEMBER.name(), Role.ADMIN.name());

        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint((request, response, e) ->
                {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        UserDetailsService userDetailService = userSecurityServices;

        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.setExposedHeaders(Arrays.asList("Content-type","Authorization"));
        configuration.setAllowCredentials(true);
        //configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
