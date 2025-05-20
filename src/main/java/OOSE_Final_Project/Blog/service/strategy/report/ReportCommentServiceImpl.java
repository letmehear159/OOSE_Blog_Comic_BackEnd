package OOSE_Final_Project.Blog.service.strategy.report;

import OOSE_Final_Project.Blog.dto.req.ReportReq;
import OOSE_Final_Project.Blog.dto.res.ReportRes;
import OOSE_Final_Project.Blog.entity.report.ReportComment;
import OOSE_Final_Project.Blog.mapper.ReportMapper;
import OOSE_Final_Project.Blog.repository.ReportCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("reportCommentServiceImpl")
public class ReportCommentServiceImpl implements IReportCommentService {

    @Autowired
    private ReportCommentRepository reportCommentRepository;

    @Autowired
    ReportMapper reportMapper;


    @Override
    public ReportRes createReport(ReportReq reportReq) {

        ReportComment reportComment = new ReportComment();
        reportMapper.updateReportCommentFromDto(reportReq, reportComment);


        // Đảm bảo reason không rỗng
        if (reportComment.getReason() == null || reportComment.getReason()
                                                              .trim()
                                                              .isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be empty");
        }

        // Đảm bảo type không null
        if (reportComment.getEReportType() == null) {
            throw new IllegalArgumentException("Report type cannot be null");
        }

        // Content và url có thể null, không cần kiểm tra

        reportComment = reportCommentRepository.save(reportComment);
        return changeToRes(reportComment);
    }

    @Override
    public List<ReportRes> getAllReports() {
        var reports = reportCommentRepository.findAll();
        return changeToRes(reports);
    }

    @Override
    public void deleteReport(long id) {
        if (!reportCommentRepository.existsById(id)) {
            throw new IllegalArgumentException("ReportComment not found with id: " + id);
        }
        reportCommentRepository.deleteById(id);
    }

    @Override
    public List<ReportRes> getUnHandledReports() {
        var reports = reportCommentRepository.findByHandledFalseAndReadTrue();
        return changeToRes(reports);
    }

    @Override
    public List<ReportRes> getUnReadReports() {
        var reports = reportCommentRepository.findByReadFalse();
        return changeToRes(reports);
    }

    @Override
    public ReportRes markAsRead(long id) {
        ReportComment reportComment = reportCommentRepository.findById(id)
                                                             .orElseThrow(() -> new IllegalArgumentException(
                                                                     "ReportBlog not found with id: " + id));
        reportComment.setRead(true);
        reportComment = reportCommentRepository.save(reportComment);
        return changeToRes(reportComment);
    }

    @Override
    public ReportRes markAsHandled(long id) {
        ReportComment reportComment = reportCommentRepository.findById(id)
                                                             .orElseThrow(() -> new IllegalArgumentException(
                                                                     "ReportBlog not found with id: " + id));
        reportComment.setHandled(true);
        reportComment.setRead(true);
        reportComment = reportCommentRepository.save(reportComment);
        return changeToRes(reportComment);
    }


    ReportRes changeToRes(ReportComment reportComment) {
        ReportRes reportRes = new ReportRes();
        reportMapper.updateReportCommentResponseFromEntity(reportComment, reportRes);
        return reportRes;
    }

    List<ReportRes> changeToRes(List<ReportComment> reportComments) {
        return reportComments.stream()
                             .map(this::changeToRes)
                             .collect(Collectors.toList());
    }
}
