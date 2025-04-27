package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.ReportBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportBlogRepository extends JpaRepository<ReportBlog, Long> {


}
