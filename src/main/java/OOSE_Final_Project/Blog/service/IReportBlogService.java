package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.ReportReq;
import OOSE_Final_Project.Blog.entity.report.ReportBlog;
import OOSE_Final_Project.Blog.enums.EReportType;

import java.util.List;
import java.util.Optional;

public interface IReportBlogService {
    ReportBlog createReportBlog(ReportReq reportReq);

    List<ReportBlog> getAllReportBlogs();

    Optional<ReportBlog> getReportBlogById(Long id);

    List<ReportBlog> getReportBlogsByUserId(Long userId);

    List<ReportBlog> getReportBlogsByBlogId(Long blogId);

    List<ReportBlog> getReportBlogsByType(EReportType type);

    void deleteReportBlog(Long id);

    ReportBlog markAsRead(Long id);
    ReportBlog markAsHandled(Long id);
}
