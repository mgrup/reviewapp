package com.review.config;

import com.review.authentication.CustomAuthenticationProvider;
import com.review.authentication.CustomAuthenticationSuccessHandler;
import com.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
////                .httpBasic()
////                .and()
//                .csrf().and()
//                .authorizeRequests()
//                .antMatchers("/index.html",
//                        "/",
//                        "/home",
//                        "/login",
//                        "/rest/api/user/registration",
//                        "/rest/api/token")
//                .permitAll()
//                .anyRequest().authenticated()
//                .and().csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.authorizeRequests()
                .antMatchers("/index.html/",
                        "/home",
                        "/rest/api/token",
                        "/login",
                        "/rest/api/user",
                        "/rest/api/user/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
//                .defaultSuccessUrl("/home")
//                .failureUrl("/error")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
//                    httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                    System.out.println(e.getMessage());
                })
                .permitAll();
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, e) -> {
//                    String json = String.format("{\"message\": \"%s\"}", e.getMessage());
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.setContentType("application/json");
//                    response.setCharacterEncoding("UTF-8");
//                    response.getWriter().write(json);
//                });


        http.csrf().disable();

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
//        auth.authenticationProvider(new CustomAuthenticationProvider(userService, passwordEncoder()));
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService);
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(new CustomAuthenticationProvider(userService, passwordEncoder()));
////        auth.inMemoryAuthentication().withUser("email").password("password").roles("USER");
//    }


}
