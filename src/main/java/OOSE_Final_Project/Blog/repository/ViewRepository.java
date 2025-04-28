package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {


    Optional<View> findByBlogId(Long blogId);
}
