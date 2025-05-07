package OOSE_Final_Project.Blog.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure()
                              .ignoreIfMissing()  // không lỗi nếu không có file .env
                              .load();

        // Đặt các biến môi trường vào System property để Spring Boot đọc được
        setIfPresent("MAIL_USERNAME", dotenv);
        setIfPresent("MAIL_PASSWORD", dotenv);
        setIfPresent("FRONT_END_URL", dotenv);
        setIfPresent("SECRET_KEY", dotenv);
        setIfPresent("GOOGLE_CLIENT_SECRET", dotenv);
        setIfPresent("BACK_END_URL", dotenv);

    }

    private void setIfPresent(String key, Dotenv dotenv) {
        String value = dotenv.get(key);
        if (value != null) {
            System.setProperty(key, value);
        }
    }
}
