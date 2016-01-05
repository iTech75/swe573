//package com.itech75.acp.security.spring;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//        		.antMatchers("/resources/**").permitAll()
//        		.antMatchers("/webjars/**").permitAll()
//        		.antMatchers("/register/**").permitAll()
//        		.antMatchers("/errors/**").permitAll()
//        		.antMatchers("/logins**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//            .logout()
//            	.permitAll()
//            	.and()
//            .authenticationProvider(new AcpAuthenticationProvider());
//    }
//	
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("tekin").password("password").roles("USER","ADMIN");
//    }
//}