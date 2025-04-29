package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.ReportReq;
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

    @Named("mapReporter")
    User mapAuthor(Long userId) {
        if(userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    @Named("mapBlog")
    Blog mapBlog(Long blogId) {
        if(blogId == null) {
            return null;
        }
        return blogRepository.findById(blogId)
                             .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));
    }

    @Named("mapComment")
    Comment mapComment(Long commentId) {
        if(commentId == null) {
            return null;
        }
        return commentRepository.findById(commentId)
                                .orElseThrow(
                                        () -> new IllegalArgumentException("Comment not found with id: " + commentId));
    }

}
