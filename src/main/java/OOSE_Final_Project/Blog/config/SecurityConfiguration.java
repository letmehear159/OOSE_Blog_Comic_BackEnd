package OOSE_Final_Project.Blog.config;

import OOSE_Final_Project.Blog.service.impl.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private static final List<String> PUBLIC_URLS = List.of("/api/v1/auth/**");

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2SuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {
        http
                .cors(c -> c.disable())
                .csrf(csrf -> csrf.disable()) // Tắt CSRF để đơn giản hóa
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_URLS.toArray(new String[0]))
                        .permitAll() // Cho phép tất cả yêu cầu mà không cần xác thực
                        .anyRequest()
                        .authenticated()

                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())
                                                      .authenticationEntryPoint(
                                                              customAuthenticationEntryPoint)
                                                      .jwt(jwt -> jwt.jwtAuthenticationConverter(
                                                              jwtAuthenticationConverter()))
                )
                .oauth2Login(oauth2 ->
                                     oauth2.userInfoEndpoint(userInfo -> userInfo.userService(
                                                   customOAuth2UserService))
                                           .successHandler(successHandler)
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("permission");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


}
