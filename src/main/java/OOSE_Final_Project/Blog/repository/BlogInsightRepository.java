package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.blog.BlogInsight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogInsightRepository extends JpaRepository<BlogInsight, Long> {

    List<BlogInsight> findByBlogCharacterId(Long id);
}
