package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.ReportReq;
import OOSE_Final_Project.Blog.entity.report.ReportComment;
import OOSE_Final_Project.Blog.enums.EReportType;

import java.util.List;
import java.util.Optional;

public interface IReportCommentService {
    ReportComment createReportComment(ReportReq reportReq);

    List<ReportComment> getAllReportComments();

    Optional<ReportComment> getReportCommentById(Long id);

    List<ReportComment> getReportCommentsByUserId(Long userId);

    List<ReportComment> getReportCommentsByCommentId(Long commentId);

    List<ReportComment> getReportCommentsByType(EReportType type);

    void deleteReportComment(Long id);

    ReportComment markAsRead(Long id);

    ReportComment markAsHandled(Long id);
}
