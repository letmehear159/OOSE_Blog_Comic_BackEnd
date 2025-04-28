package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.report.ReportComment;
import OOSE_Final_Project.Blog.enums.EReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {


    List<ReportComment> findByReporterId(Long userId);

    List<ReportComment> findByCommentId(Long commentId);

    List<ReportComment> findByType(EReportType type);
}
