package OOSE_Final_Project.Blog.service.strategy.report;

import OOSE_Final_Project.Blog.dto.req.ReportReq;
import OOSE_Final_Project.Blog.dto.res.ReportRes;
import OOSE_Final_Project.Blog.entity.report.ReportBlog;
import OOSE_Final_Project.Blog.mapper.ReportMapper;
import OOSE_Final_Project.Blog.repository.ReportBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("reportBlogServiceImpl")
public class ReportBlogServiceImpl implements IReportBlogService {


    @Autowired
    ReportMapper reportMapper;

    @Autowired
    ReportBlogRepository reportBlogRepository;

    @Override
    public ReportRes createReport(ReportReq reportReq) {
        ReportBlog reportBlog = new ReportBlog();
        reportMapper.updateReportBlogFromDto(reportReq, reportBlog);

        // Đảm bảo reason không rỗng
        if (reportBlog.getReason() == null || reportBlog.getReason()
                                                        .trim()
                                                        .isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be empty");
        }

        // Đảm bảo type không null
        if (reportBlog.getEReportType() == null) {
            throw new IllegalArgumentException("Report type cannot be null");
        }

        // Content và url có thể null, không cần kiểm tra


        reportBlog = reportBlogRepository.save(reportBlog);
        return changeToRes(reportBlog);

    }

    @Override
    public List<ReportRes> getAllReports() {
        var reports = reportBlogRepository.findAll();
        return changeToRes(reports);
    }

    @Override
    public void deleteReport(long id) {
        if (!reportBlogRepository.existsById(id)) {
            throw new IllegalArgumentException("ReportBlog not found with id: " + id);
        }
        reportBlogRepository.deleteById(id);
    }

    @Override
    public ReportRes markAsRead(long id) {
        ReportBlog reportBlog = reportBlogRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException(
                                                            "ReportBlog not found with id: " + id));
        reportBlog.setRead(true);
        reportBlog = reportBlogRepository.save(reportBlog);
        return changeToRes(reportBlog);

    }

    @Override
    public ReportRes markAsHandled(long id) {
        ReportBlog reportBlog = reportBlogRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException(
                                                            "ReportBlog not found with id: " + id));
        reportBlog.setHandled(true);
        reportBlog.setRead(true);
        reportBlog = reportBlogRepository.save(reportBlog);
        return changeToRes(reportBlog);
    }

    @Override
    public List<ReportRes> getUnHandledReports() {
        var reports = reportBlogRepository.findByHandledFalseAndReadTrue();
        return changeToRes(reports);
    }

    @Override
    public List<ReportRes> getUnReadReports() {
        var reports = reportBlogRepository.findByReadFalse();
        return changeToRes(reports);
    }

    ReportRes changeToRes(ReportBlog reportBlog) {
        ReportRes reportRes = new ReportRes();
        reportMapper.updateReportBlogResponseFromEntity(reportBlog, reportRes);
        return reportRes;
    }

    List<ReportRes> changeToRes(List<ReportBlog> reportBlogs) {
        return reportBlogs.stream()
                          .map(this::changeToRes)
                          .collect(Collectors.toList());
    }
}
