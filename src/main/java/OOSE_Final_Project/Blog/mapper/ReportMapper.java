package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.ReportReq;
import OOSE_Final_Project.Blog.dto.res.ReportRes;
import OOSE_Final_Project.Blog.dto.res.blog.BlogReportRes;
import OOSE_Final_Project.Blog.dto.res.comment.CommentReportRes;
import OOSE_Final_Project.Blog.dto.res.user.UserReportRes;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.entity.report.ReportBlog;
import OOSE_Final_Project.Blog.entity.report.ReportComment;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {UserRepository.class, BlogRepository.class, CommentRepository.class})
public abstract class ReportMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected BlogRepository blogRepository;

    @Autowired
    protected CommentRepository commentRepository;

    @Mapping(target = "reporter", source = "userId", qualifiedByName = "mapReporter")
    @Mapping(target = "blog", source = "blogId", qualifiedByName = "mapBlog")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateReportBlogFromDto(ReportReq source, @MappingTarget ReportBlog target);

    @Mapping(target = "comment", source = "commentId", qualifiedByName = "mapComment")
    @Mapping(target = "reporter", source = "userId", qualifiedByName = "mapReporter")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateReportCommentFromDto(ReportReq source, @MappingTarget ReportComment target);

    @Mapping(target = "reporterRes", source = "reporter", qualifiedByName = "mapReporterRes")
    @Mapping(target = "blogRes", source = "blog", qualifiedByName = "mapBlogRes")
    @Mapping(target = "violationType", source = "EReportType")
    @Mapping(target = "reportType", expression = "java(\"Blog\")")
    public abstract void updateReportBlogResponseFromEntity(ReportBlog source, @MappingTarget ReportRes target);

    @Mapping(target = "reporterRes", source = "reporter", qualifiedByName = "mapReporterRes")
    @Mapping(target = "commentRes", source = "comment", qualifiedByName = "mapCommentRes")
    @Mapping(target = "violationType", source = "EReportType")
    @Mapping(target = "reportType", expression = "java(\"Comment\")")
    public abstract void updateReportCommentResponseFromEntity(ReportComment source, @MappingTarget ReportRes target);


    @Named("mapReporterRes")
    UserReportRes mapReporterRes(User reporter) {
        return new UserReportRes.Builder().avatar(reporter.getAvatar())
                                          .id(reporter.getId())
                                          .level(reporter.getLevel())
                                          .displayName(reporter.getDisplayName())
                                          .build();
    }

    @Named("mapBlogRes")
    BlogReportRes mapBlogRes(Blog blog) {
        return BlogReportRes.builder()
                            .authorDisplayName(blog.getAuthor()
                                                   .getDisplayName())
                            .blogTitle(blog.getTitle())
                            .authorAvatar(blog.getAuthor()
                                              .getAvatar())
                            .blogThumbnail(blog.getThumbnail())
                            .build();
    }


    @Named("mapCommentRes")
    CommentReportRes mapCommentId(Comment comment) {
        return CommentReportRes.builder()
                               .content(comment.getContent())
                               .userDisplayName(comment.getAuthor()
                                                       .getUsername())
                               .avatar(comment.getAuthor()
                                              .getAvatar())
                               .build();
    }

    @Named("mapReporter")
    User mapAuthor(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    @Named("mapBlog")
    Blog mapBlog(Long blogId) {
        if (blogId == null) {
            return null;
        }
        return blogRepository.findById(blogId)
                             .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));
    }

    @Named("mapComment")
    Comment mapComment(Long commentId) {
        if (commentId == null) {
            return null;
        }
        return commentRepository.findById(commentId)
                                .orElseThrow(
                                        () -> new IllegalArgumentException("Comment not found with id: " + commentId));
    }

}
