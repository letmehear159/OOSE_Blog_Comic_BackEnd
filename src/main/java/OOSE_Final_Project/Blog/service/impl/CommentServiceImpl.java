package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.CommentReq;
import OOSE_Final_Project.Blog.dto.res.comment.CommentRes;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.mapper.CommentMapper;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.service.ICommentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    @Override
    public CommentRes createComment(CommentReq commentReq) {

        Comment comment = new Comment();
        commentMapper.updateCommentFromDto(commentReq, comment);


        if (comment.getParent() != null) {
            if (comment.getParent()
                       .getBlog()
                       .getId() != commentReq.getBlogId()) {
                throw new IllegalArgumentException("Comment parent's blog id not match with comment request's blog id");
            }
        }
        // Đảm bảo content không rỗng
        if (comment.getContent() == null || comment.getContent()
                                                   .trim()
                                                   .isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }

        var result = commentRepository.save(comment);
        CommentRes commentRes = new CommentRes();
        commentMapper.updateCommentResponseFromEntity(comment, commentRes);
        return commentRes;
    }

    @Override
    public List<CommentRes> getAllComments() {
        var comments = commentRepository.findAll();
        return this.changeEntityToRes(comments);
    }


    @Override
    public List<CommentRes> getCommentsByBlogId(Long blogId) {
        var comments = commentRepository.findByBlogId(blogId);
        return this.changeEntityToRes(comments);

    }


    @Override
    public List<CommentRes> getChildComments(Long parentId) {
        var comments = commentRepository.findByParentId(parentId);
        return this.changeEntityToRes(comments);
    }

    @Override
    public List<CommentRes> getCommentsByParentIsNullAndBlogId(Long blogId) {
        var comments = commentRepository.findByParentIsNullAndBlogId(blogId);
        return this.changeEntityToRes(comments);
    }

    @Override
    public int getCommentCountByBlogId(Long blogId) {
        var comments = commentRepository.findByBlogId(blogId);
        return comments.size();
    }

    @Override
    public CommentRes updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id)
                                           .orElseThrow(() -> new IllegalArgumentException(
                                                   "Comment not found with id: " + id));

        // Chỉ cập nhật content nếu không rỗng
        if (commentDetails.getContent() != null && !commentDetails.getContent()
                                                                  .trim()
                                                                  .isEmpty()) {
            comment.setContent(commentDetails.getContent());
        }

        // Không cho phép thay đổi author, blog, hoặc parent để đảm bảo tính nhất quán
        comment = commentRepository.save(comment);
        CommentRes commentRes = new CommentRes();
        commentMapper.updateCommentResponseFromEntity(comment, commentRes);
        return commentRes;
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                                           .orElseThrow(() -> new IllegalArgumentException(
                                                   "Comment not found with id: " + id));

        // Xóa tất cả comment con do cascade = CascadeType.ALL
        commentRepository.delete(comment);
    }

    public List<CommentRes> changeEntityToRes(List<Comment> comments) {
        return comments.stream()
                       .map(
                               comment -> {
                                   CommentRes commentRes = new CommentRes();
                                   commentMapper.updateCommentResponseFromEntity(comment, commentRes);
                                   return commentRes;
                               }
                       )
                       .toList();
    }
}
