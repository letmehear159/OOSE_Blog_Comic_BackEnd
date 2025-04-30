package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.report.ReportComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {

    List<ReportComment> findByHandledFalse();
}
