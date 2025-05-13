package OOSE_Final_Project.Blog.observer;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.comment.CommentRes;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentBlogObserver implements Observer {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    INotificationService notificationService;

    @Override
    public void update(Object data) {
        // Cần có dữ liệu author id
        CommentRes commentRes = (CommentRes) data;
        Comment comment = commentRepository.findById(commentRes.getId())
                .orElseThrow(() -> new RuntimeException("Error in saving comment"));

        long authorId = comment.getBlog()
                .getAuthor()
                .getId();

        if (authorId == commentRes.getUserCommentResponse()
                .getUserId()) {
            return;
        }

        NotificationReq notificationReq = NotificationReq.builder()
                .url(null)
                .receiverId(authorId)
                .senderId(commentRes.getUserCommentResponse()
                        .getUserId())
                .message(commentRes.getUserCommentResponse()
                        .getDisplayName() +
                        " vừa bình luận về blog của bạn")
                .build();

        notificationService.createNotification(notificationReq);

    }

    @Override
    public String getPublisherType() {
        return "CommentPublisher";
    }
}
