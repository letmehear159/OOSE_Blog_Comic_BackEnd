package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.CommentReq;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
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
public abstract class CommentMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Mapping(target = "author", source = "userId", qualifiedByName = "mapAuthor")
    @Mapping(target = "blog", source = "blogId", qualifiedByName = "mapBlog")
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "mapCommentParent")
    public abstract void updateCommentFromDto(CommentReq source, @MappingTarget Comment target);


    @Named("mapAuthor")
    User mapAuthor(Long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    @Named("mapBlog")
    Blog mapBlog(Long blogId) {
        return blogRepository.findById(blogId)
                             .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));
    }

    @Named("mapCommentParent")
    Comment mapCommentParent(Long parentId) {
        if (parentId == null) {
            return null;
        }
        return commentRepository.findById(parentId)
                                .orElseThrow(
                                        () -> new IllegalArgumentException(
                                                "Parent comment not found with id: " + parentId));
    }
}
