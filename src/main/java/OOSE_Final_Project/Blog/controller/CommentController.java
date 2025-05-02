package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.CommentReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.comment.CommentRes;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.observer.CommentPublisher;
import OOSE_Final_Project.Blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {

    @Autowired
    ICommentService commentService;

    @Autowired
    CommentPublisher commentPublisher;

    @GetMapping("/comment-in-blog/{blogId}")
    public ApiResponse<List<CommentRes>> getParentCommentByBlogId(@PathVariable String blogId) {
        List<CommentRes> result = commentService.getCommentsByParentIsNullAndBlogId(Long.valueOf(blogId));
        return new ApiResponse<List<CommentRes>>(
                HttpStatus.OK, "Get highest comments (parent=null) in a blog", result, null);
    }

    @GetMapping("/child-comment/{commentId}")
    public ApiResponse<List<CommentRes>> getChildCommentByComment(@PathVariable String commentId) {
        List<CommentRes> result = commentService.getChildComments(Long.valueOf(commentId));
        return new ApiResponse<>(HttpStatus.OK, "Get child comment of a comment ", result, null);
    }

    @GetMapping("")
    public ApiResponse<List<CommentRes>> getAllComments() {
        List<CommentRes> result = commentService.getAllComments();
        return new ApiResponse<>(HttpStatus.OK, "Get all comments", result, null);
    }

    @PostMapping("")
    public ApiResponse<CommentRes> createComment(@RequestBody CommentReq commentReq) {

        CommentRes result = commentService.createComment(commentReq);
        commentPublisher.notifyObservers(result);

        return new ApiResponse<>(HttpStatus.OK, "Create comment", result, null);
    }

    @PutMapping("/{commentId}")
    public ApiResponse<CommentRes> updateComment(@PathVariable String commentId, @RequestBody Comment commentReq) {
        CommentRes result = commentService.updateComment(Long.valueOf(commentId), commentReq);
        return new ApiResponse<>(HttpStatus.OK, "Update comment", result, null);
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Boolean> deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(Long.valueOf(commentId));
        return new ApiResponse<>(HttpStatus.OK, "Delete comment", Boolean.TRUE, null);
    }
}
