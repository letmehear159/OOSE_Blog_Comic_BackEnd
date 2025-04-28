package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.ReportReq;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.entity.report.ReportBlog;
import OOSE_Final_Project.Blog.entity.report.ReportComment;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
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
    public abstract void updateReportBlogFromDto(ReportReq source, @MappingTarget ReportBlog target);

    @Mapping(target = "comment", source = "commentId", qualifiedByName = "mapComment")
    @Mapping(target = "reporter", source = "userId", qualifiedByName = "mapReporter")
    public abstract void updateReportCommentFromDto(ReportReq source, @MappingTarget ReportComment target);

    @Named("mapReporter")
    User mapAuthor(Long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    @Named("mapBlog")
    Blog mapBlog(Long blogId) {
        return blogRepository.findById(blogId)
                             .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));
    }

    @Named("mapComment")
    Comment mapComment(Long commentId) {
        return commentRepository.findById(commentId)
                                .orElseThrow(
                                        () -> new IllegalArgumentException("Comment not found with id: " + commentId));
    }

}
