package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.CommentReq;
import OOSE_Final_Project.Blog.dto.res.comment.CommentRes;
import OOSE_Final_Project.Blog.entity.Comment;

import java.util.List;

public interface ICommentService {
    CommentRes createComment(CommentReq comment);

    List<CommentRes> getAllComments();

    List<CommentRes> getCommentsByBlogId(Long blogId);


    List<CommentRes> getChildComments(Long parentId);

    CommentRes updateComment(Long id, Comment commentDetails);

    void deleteComment(Long id);

    public List<CommentRes> getCommentsByParentIsNullAndBlogId(Long blogId);

}
