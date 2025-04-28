package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.ReportReq;
import OOSE_Final_Project.Blog.entity.report.ReportComment;
import OOSE_Final_Project.Blog.enums.EReportType;
import OOSE_Final_Project.Blog.mapper.ReportMapper;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.repository.ReportCommentRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.IReportCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportCommentServiceImpl implements IReportCommentService {

    private final ReportCommentRepository reportCommentRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    public ReportCommentServiceImpl(
            ReportCommentRepository reportCommentRepository,
            UserRepository userRepository,
            CommentRepository commentRepository) {
        this.reportCommentRepository = reportCommentRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ReportComment createReportComment(ReportReq reportReq) {

        ReportComment reportComment = new ReportComment();
        reportMapper.updateReportCommentFromDto(reportReq, reportComment);


        // Đảm bảo reason không rỗng
        if (reportComment.getReason() == null || reportComment.getReason()
                                                              .trim()
                                                              .isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be empty");
        }

        // Đảm bảo type không null
        if (reportComment.getType() == null) {
            throw new IllegalArgumentException("Report type cannot be null");
        }

        // Content và url có thể null, không cần kiểm tra

        return reportCommentRepository.save(reportComment);
    }

    @Override
    public List<ReportComment> getAllReportComments() {
        return reportCommentRepository.findAll();
    }

    @Override
    public Optional<ReportComment> getReportCommentById(Long id) {
        return reportCommentRepository.findById(id);
    }

    @Override
    public List<ReportComment> getReportCommentsByUserId(Long userId) {
        return reportCommentRepository.findByReporterId(userId);
    }

    @Override
    public List<ReportComment> getReportCommentsByCommentId(Long commentId) {
        return reportCommentRepository.findByCommentId(commentId);
    }

    @Override
    public List<ReportComment> getReportCommentsByType(EReportType type) {
        return reportCommentRepository.findByType(type);
    }


    @Override
    public void deleteReportComment(Long id) {
        if (!reportCommentRepository.existsById(id)) {
            throw new IllegalArgumentException("ReportComment not found with id: " + id);
        }
        reportCommentRepository.deleteById(id);
    }

    @Override
    public ReportComment markAsRead(Long id) {
        ReportComment reportComment = reportCommentRepository.findById(id)
                                                             .orElseThrow(() -> new IllegalArgumentException(
                                                                     "ReportBlog not found with id: " + id));
        reportComment.setRead(true);
        return reportCommentRepository.save(reportComment);
    }

    @Override
    public ReportComment markAsHandled(Long id) {
        ReportComment reportComment = reportCommentRepository.findById(id)
                                                             .orElseThrow(() -> new IllegalArgumentException(
                                                                     "ReportBlog not found with id: " + id));
        reportComment.setHandled(true);
        reportComment.setRead(true);
        return reportCommentRepository.save(reportComment);
    }

}
