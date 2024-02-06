package realsoft.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;


import  java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebSecurityCustomizer {



        @Bean
        public SecurityFilterChain defaultSecurityFilerChain(HttpSecurity http) throws Exception{
            http.cors(cors -> cors.configurationSource( request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:8081"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(List.of("*"));
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }))
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(
                            (requests) -> requests
                                    .requestMatchers("/admin/**")
                                    .hasRole("ADMIN")
                                    .requestMatchers(
                                            "/order/**",
                                            "/user/**"
                                            )
                                    .hasAnyRole("USER","ADMIN")
                                    .requestMatchers("/register/addAccount").permitAll()
                                    .anyRequest().permitAll()
                    );
            http.formLogin(Customizer.withDefaults());
            http.httpBasic(Customizer.withDefaults());
            return http.build();

        }
        
       /* @Bean
        public UserDetailsService userDetailsService(){
            UserDetails user1 = User.builder()
                    .username("muhammad")
                    .password("12345")
                    .roles("ADMIN")
                    .build();
            
            UserDetails user2 = User.builder()
                    .username("xoji")
                    .password("xoji12345")
                    .roles("USER")
                    .build();
            return new InMemoryUserDetailsManager(user1,user2);
                    
        }*/

        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration authenticationConfiguration
        ) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Override
    public void customize(WebSecurity web){
            web.ignoring().requestMatchers("/v3/api-docs/**, /swagger-ui.html, /swagger-ui/**");

    }
}


