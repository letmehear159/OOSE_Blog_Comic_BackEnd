package OOSE_Final_Project.Blog.util;

import OOSE_Final_Project.Blog.dto.UserClaim;
import OOSE_Final_Project.Blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityUtil {

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Value("${secrect-key}")
    private String jwtKey;

    @Value("${jwt.access-token-valid-in-seconds}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-valid-in-seconds}")
    private long refreshTokenExpiration;


    public String createAccessToken(User user) {

        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);
        String role = user.getRole()
                          .toString();
        List<String> scopes = new ArrayList<>();
        scopes.add(role);

        UserClaim userClaim = UserClaim.builder()
                                       .role(user.getRole())
                                       .username(user.getUsername())
                                       .email(user.getEmail())
                                       .build();

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuedAt(now)
                                          .expiresAt(validity)
                                          .subject(userClaim.getUsername())
                                          .claim("user", userClaim)
                                          .claim("permission", scopes)
                                          .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }


    public String createRefreshToken(User user) {

        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuedAt(now)
                                          .expiresAt(validity)
                                          .subject(user.getUsername())
                                          .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public Jwt checkValidRefreshToken(String refreshToken) {
        try{
            return jwtDecoder.decode(refreshToken);
        }catch (Exception e){

            throw e;
        }
    }

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }
}
