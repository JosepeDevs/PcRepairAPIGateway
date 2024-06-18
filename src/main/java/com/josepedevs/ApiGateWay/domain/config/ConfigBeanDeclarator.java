package com.josepedevs.apigateway.domain.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.josepedevs.apigateway.domain.reepository.AuthRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ConfigBeanDeclarator {
	
	private final AuthRepository repository;

	@Bean
    @LoadBalanced
    //it tells srping to integrate with Ribbon for client-side load balancing
	//By default, Spring Cloud uses Ribbon for service discovery and load balancing. 
	//When you declare a RestTemplate bean with @LoadBalanced, Spring Cloud automatically configures Ribbon
	//behind the scenes. You don't need to manually configure Ribbon unless you require custom settings
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	
    @Bean
    public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			//revisar que puede que falte algo aquí, no veo la asignacion del creado ouserDetails con UserDetails obtenidos.
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				return repository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("User not found when authenticating"));
			}
		};
	}
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    	authProvider.setUserDetailsService(userDetailsService());
    	authProvider.setPasswordEncoder(passwordEncoder());
    	return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
    }
}
