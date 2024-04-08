//package com.tokioschool.filmotokio.security;
//
//import com.tokioschool.filmotokio.security.jwt.JwtAuthenticationEntryPoint;
//import com.tokioschool.filmotokio.security.jwt.JwtRequestFilter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
//
//    @Autowired
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Autowired
//    private FilmoTokioUserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//
//    @Configuration
//    @Order(2)
//    public static class MvcSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//
//            http
//                    .csrf().disable()
//                    .antMatcher("/**").authorizeRequests()
//                    .antMatchers("/swagger-ui/**").permitAll()
//                    .antMatchers("/v3/api-docs/**").permitAll()
//                    .antMatchers("/swagger-ui.html").permitAll()
//                    .antMatchers("/profile/**").authenticated()
//                    .antMatchers("/film/create").authenticated()
//                    .antMatchers("/film/score/**").authenticated()
//                    .antMatchers("/user/register").hasRole("ADMIN")
//                    .antMatchers("/api/login").permitAll()
//                    .antMatchers("/api/**").authenticated()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/", true)
//                    .and()
//                    .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutSuccessUrl("/")
//                    .and()
//                    .addFilterBefore(jw)
//
//        }
//    }
//
//
//    @Configuration
//    @Order(1)
//    public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//
//
//        @Autowired
//        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//        @Autowired
//        private JwtRequestFilter jwtRequestFilter;
//
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            System.out.println("RestApiSecurityConfig");
//
//            http
//                    .requestMatchers(req ->
//                            req.antMatchers("/error", "/api/**"))
//                    .csrf().disable()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/error").permitAll() // Permite acesso à página de erro sem autenticação
//                    .antMatchers("/v3/api-docs/**").permitAll()
//                    .antMatchers("/swagger-ui.html").permitAll()
//                    .antMatchers("/api/login").permitAll()
//                    .antMatchers("/api/**").authenticated()
//                    .anyRequest().permitAll()
//                    .and()
//                    .formLogin()
//                    .and()
//                    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
//        }
//
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
//





package com.tokioschool.filmotokio.security;

import com.tokioschool.filmotokio.security.jwt.JwtAuthenticationEntryPoint;
import com.tokioschool.filmotokio.security.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final FilmoTokioUserDetailsService userDetailsService;


    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private JwtRequestFilter jwtRequestFilter;

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(FilmoTokioUserDetailsService userDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, JwtRequestFilter jwtRequestFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .antMatcher("/**").authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/film/create").authenticated()
                .antMatchers("/film/score/**").authenticated()
                .antMatchers("/user/register").hasRole("ADMIN")
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()

                .and().csrf()// Ignorar CSRF para URLs da API
                .ignoringAntMatchers("/api/**")


//                 Usar Token CSRF como Cookie
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())


                // Configurar políticas de sessão, login, logout, etc.
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)

                .and()
                .exceptionHandling().accessDeniedPage("/denied")


                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }



}



