package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.CommentReq;
import OOSE_Final_Project.Blog.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    Comment createComment(CommentReq comment);

    List<Comment> getAllComments();

    Optional<Comment> getCommentById(Long id);

    List<Comment> getCommentsByBlogId(Long blogId);

    List<Comment> getCommentsByUserId(Long userId);

    List<Comment> getChildComments(Long parentId);

    Comment updateComment(Long id, Comment commentDetails);

    void deleteComment(Long id);

    public List<Comment> getCommentsByParentIsNullAndBlogId(Long blogId);

}
