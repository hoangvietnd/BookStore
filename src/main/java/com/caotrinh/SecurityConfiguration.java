package com.caotrinh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.caotrinh.oauth.OAuth2LoginSuccessHandler;
import com.caotrinh.service.CustomOAuth2UserService;
import com.caotrinh.service.CustomerService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Autowired
	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
//		http.authorizeRequests().anyRequest().permitAll();
		http .authorizeRequests()
			.antMatchers("/oauth2/**").permitAll()
			.antMatchers("/admin/**").hasAuthority("admin")
			.antMatchers("/user/**").hasAuthority("user")
            .antMatchers(
                "/addCustomerForm**","/checkUserName","/index",
                "/resources/**",
                "/image/**",
                "/i18/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .oauth2Login()
            	.loginPage("/login")
            	.userInfoEndpoint().userService(customOAuth2UserService)
            	.and()
            	.successHandler(oAuth2LoginSuccessHandler)
            .and()
            .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout").permitAll()
            .and().exceptionHandling().accessDeniedPage("/403");
	}
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(customerService);
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
