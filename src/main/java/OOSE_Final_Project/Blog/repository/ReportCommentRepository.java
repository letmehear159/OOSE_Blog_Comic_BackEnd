package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.ReportComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {


}
