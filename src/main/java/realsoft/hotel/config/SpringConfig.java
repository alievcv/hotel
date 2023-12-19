package realsoft.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import  java.util.List;

@Configuration
public class SpringConfig {
        @Bean
        public SecurityFilterChain defaultSecurityFilerChain(HttpSecurity http) throws Exception{
            http.cors(cors -> cors.configurationSource( request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:8081/show"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(List.of("*"));
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }))
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(
                            (requests) -> requests.requestMatchers(HttpMethod.GET,"/show")
                                    .permitAll()
                    );
            http.formLogin(Customizer.withDefaults());
            http.httpBasic(Customizer.withDefaults());
            return http.build();




        }
}
