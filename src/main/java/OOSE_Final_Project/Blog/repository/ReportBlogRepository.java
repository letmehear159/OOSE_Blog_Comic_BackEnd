package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.report.ReportBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportBlogRepository extends JpaRepository<ReportBlog, Long> {


    List<ReportBlog> findByHandledFalseAndReadTrue();

    List<ReportBlog> findByReadFalse();

}
