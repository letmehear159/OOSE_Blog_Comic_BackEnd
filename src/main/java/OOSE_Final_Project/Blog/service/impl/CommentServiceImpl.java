package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.CommentReq;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.mapper.CommentMapper;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    CommentMapper commentMapper;

    @Override
    public Comment createComment(CommentReq commentReq) {

        Comment comment = new Comment();
        commentMapper.updateCommentFromDto(commentReq, comment);


        // Đảm bảo content không rỗng
        if (comment.getContent() == null || comment.getContent()
                                                   .trim()
                                                   .isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getCommentsByBlogId(Long blogId) {
        return commentRepository.findByBlogId(blogId);
    }

    @Override
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByAuthorId(userId);
    }

    @Override
    public List<Comment> getChildComments(Long parentId) {
        return commentRepository.findByParentId(parentId);
    }

    @Override
    public List<Comment> getCommentsByParentIsNullAndBlogId(Long blogId) {
        return commentRepository.findByParentIsNullAndBlogId(blogId);
    }

    @Override
    public Comment updateComment(Long id, Comment commentDetails) {
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
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                                           .orElseThrow(() -> new IllegalArgumentException(
                                                   "Comment not found with id: " + id));

        // Xóa tất cả comment con do cascade = CascadeType.ALL
        commentRepository.delete(comment);
    }
}
