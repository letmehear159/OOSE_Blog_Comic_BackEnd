package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
