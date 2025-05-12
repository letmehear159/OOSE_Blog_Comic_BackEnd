package OOSE_Final_Project.Blog;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("OOSE_Final_Project.Blog.entity")

public class BlogApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                              .ignoreIfMissing()
                              .load();

        setIfPresent("MAIL_USERNAME", dotenv);
        setIfPresent("MAIL_PASSWORD", dotenv);
        setIfPresent("FRONT_END_URL", dotenv);
        setIfPresent("SECRET_KEY", dotenv);
        setIfPresent("GOOGLE_CLIENT_SECRET", dotenv);
        setIfPresent("BACK_END_URL", dotenv);
        setIfPresent("DATA_SOURCE", dotenv);
        setIfPresent("DB_USERNAME", dotenv);
        setIfPresent("DB_PASSWORD", dotenv);


        SpringApplication.run(BlogApplication.class, args);
    }

    private static void setIfPresent(String key, Dotenv dotenv) {
        String value = dotenv.get(key);
        if (value != null) {
            System.setProperty(key, value);
        }
    }
}
