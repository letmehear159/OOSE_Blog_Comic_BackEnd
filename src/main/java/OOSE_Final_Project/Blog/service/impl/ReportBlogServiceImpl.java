package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.ReportReq;
import OOSE_Final_Project.Blog.entity.report.ReportBlog;
import OOSE_Final_Project.Blog.enums.EReportType;
import OOSE_Final_Project.Blog.mapper.ReportMapper;
import OOSE_Final_Project.Blog.repository.ReportBlogRepository;
import OOSE_Final_Project.Blog.service.IReportBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportBlogServiceImpl implements IReportBlogService {

    private final ReportBlogRepository reportBlogRepository;

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    public ReportBlogServiceImpl(
            ReportBlogRepository reportBlogRepository
    ) {
        this.reportBlogRepository = reportBlogRepository;
    }

    @Override
    public ReportBlog createReportBlog(ReportReq reportReq) {
        ReportBlog reportBlog = new ReportBlog();
        reportMapper.updateReportBlogFromDto(reportReq, reportBlog);

        // Đảm bảo reason không rỗng
        if (reportBlog.getReason() == null || reportBlog.getReason()
                                                        .trim()
                                                        .isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be empty");
        }

        // Đảm bảo type không null
        if (reportBlog.getType() == null) {
            throw new IllegalArgumentException("Report type cannot be null");
        }

        // Content và url có thể null, không cần kiểm tra

        return reportBlogRepository.save(reportBlog);
    }

    @Override
    public List<ReportBlog> getAllReportBlogs() {
        return reportBlogRepository.findAll();
    }

    @Override
    public Optional<ReportBlog> getReportBlogById(Long id) {
        return reportBlogRepository.findById(id);
    }

    @Override
    public List<ReportBlog> getReportBlogsByUserId(Long userId) {
        return reportBlogRepository.findByReporterId(userId);
    }

    @Override
    public List<ReportBlog> getReportBlogsByBlogId(Long blogId) {
        return reportBlogRepository.findByBlogId(blogId);
    }

    @Override
    public List<ReportBlog> getReportBlogsByType(EReportType type) {
        return reportBlogRepository.findByType(type);
    }

    @Override
    public void deleteReportBlog(Long id) {
        if (!reportBlogRepository.existsById(id)) {
            throw new IllegalArgumentException("ReportBlog not found with id: " + id);
        }
        reportBlogRepository.deleteById(id);
    }

    @Override
    public ReportBlog markAsRead(Long id) {
        ReportBlog reportBlog = reportBlogRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException(
                                                            "ReportBlog not found with id: " + id));
        reportBlog.setRead(true);
        return reportBlogRepository.save(reportBlog);
    }

    @Override
    public ReportBlog markAsHandled(Long id) {
        ReportBlog reportBlog = reportBlogRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException(
                                                            "ReportBlog not found with id: " + id));
        reportBlog.setHandled(true);
        reportBlog.setRead(true);
        return reportBlogRepository.save(reportBlog);
    }
}
