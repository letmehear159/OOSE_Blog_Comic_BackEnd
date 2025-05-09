package OOSE_Final_Project.Blog.observer;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.ReactionRes;
import OOSE_Final_Project.Blog.dto.res.user.UserReactionRes;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReactionObserver implements Observer {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    INotificationService notificationService;

    @Autowired
    BlogRepository blogRepository;

    @Override
    public void update(Object data) {
        ReactionRes reactionRes = (ReactionRes) data;
        UserReactionRes sender = reactionRes.getUser();

        if (reactionRes.getBlogId() == null) {
            Comment comment = commentRepository.findById(reactionRes.getCommentId())
                    .orElseThrow(() -> new RuntimeException("Error in saving reaction"));

            NotificationReq notificationReq = NotificationReq.builder()
                    .url(null)
                    .receiverId(comment.getAuthor()
                            .getId())
                    .senderId(sender.getUserId())
                    .message(sender.getDisplayName() +
                            " đã thả cảm xúc về bình luận của bạn")
                    .build();
            notificationService.createNotification(notificationReq);
        } else {
            var blog = blogRepository.findById(reactionRes.getBlogId())
                    .orElseThrow(() -> new RuntimeException("Error in saving reaction"));

            NotificationReq notificationReq = NotificationReq.builder()
                    .url(null)
                    .receiverId(blog.getAuthor()
                            .getId())
                    .senderId(sender.getUserId())
                    .message(sender.getDisplayName() +
                            " đã thả cảm xúc về bài viết của bạn " +
                            "của " +
                            "bạn")
                    .build();
            notificationService.createNotification(notificationReq);
        }
    }

    @Override
    public String getPublisherType() {
        return "ReactionPublisher";
    }
}
