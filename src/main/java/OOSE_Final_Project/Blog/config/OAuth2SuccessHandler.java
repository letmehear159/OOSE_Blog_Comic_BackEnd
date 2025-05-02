package OOSE_Final_Project.Blog.config;

import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.enums.ELoginType;
import OOSE_Final_Project.Blog.enums.ERole;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${FRONT_END_URL}")
    private String FRONT_END_URL;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityUtil securityUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {

        // Tạo JWT dựa trên thông tin người dùng
        String jwtToken = "test";
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String displayName = oauth2AuthenticationToken.getPrincipal()
                                                      .getAttribute("name");

        String email = oauth2AuthenticationToken.getPrincipal()
                                                .getAttribute("email");

        String avatar = oauth2AuthenticationToken.getPrincipal()
                                                 .getAttribute("picture");

        User user = userRepository.findByUsernameOrEmail(email)
                                  .orElse(null);
        // Đăng ký lần đầu
        if (user == null) {
            user = User.builder()
                       .avatar(avatar)
                       .displayName(displayName)
                       .email(email)
                       .password("")
                       .loginType(ELoginType.GOOGLE)
                       .role(ERole.USER)
                       .username(displayName)
                       .build();
            userRepository.save(user);
            jwtToken = createAccessToken(user);
            String redirectUrl = FRONT_END_URL + "/callback?token=" + jwtToken;
            response.sendRedirect(redirectUrl);
        } else {
            // Trường hợp đã tạo tài khoản, có thể là tạo bằng LOCAL hoặc tạo bằng GOOGLE
            jwtToken = createAccessToken(user);
            String redirectUrl = FRONT_END_URL + "/callback?token=" + jwtToken;
            response.sendRedirect(redirectUrl);
        }
        // Redirect về FrontEnd với token (hoặc trả về JSON)

    }

    public String createAccessToken(User user) {
        return securityUtil.createAccessToken(user);
    }

}