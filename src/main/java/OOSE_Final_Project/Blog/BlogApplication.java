package OOSE_Final_Project.Blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("OOSE_Final_Project.Blog.entity")

public class BlogApplication {

    public static void main(String[] args) {


        SpringApplication.run(BlogApplication.class, args);
    }

}
