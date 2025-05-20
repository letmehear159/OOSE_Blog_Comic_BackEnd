package OOSE_Final_Project.Blog.observer;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.comment.CommentRes;
import OOSE_Final_Project.Blog.dto.res.user.UserCommentRes;
import OOSE_Final_Project.Blog.entity.Comment;
import OOSE_Final_Project.Blog.enums.EBlogType;
import OOSE_Final_Project.Blog.repository.CommentRepository;
import OOSE_Final_Project.Blog.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommentObserver implements Observer {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    INotificationService notificationService;

    @Value("${FRONT_END_URL}")
    private String FE_URL;

    @Override
    public void update(Object data) {
        // Nếu là comment cha thì không cần làm gì
        // Nếu là comment con thì cần tìm comment cha gần nhất để lấy userId thông báo.
        CommentRes commentRes = (CommentRes) data;
        Comment comment = commentRepository.findById(commentRes.getId())
                                           .orElseThrow(() -> new RuntimeException("Error in saving comment"));

        if (comment.getParent() == null) {
            return;
        }


        while (comment.getParent() != null) {
            long authorCommentParent = comment.getParent()
                                              .getAuthor()
                                              .getId();
            if (authorCommentParent == commentRes.getUserCommentResponse()
                                                 .getUserId()) {
                return;
            }
            String blogType;
            if (commentRes.getBlog()
                          .getBlogType() == EBlogType.CHARACTER) {
                blogType = "Character";
            } else {
                blogType = "Comic";
            }
            String url = FE_URL + "/" + blogType + "/" + commentRes.getBlog()
                                                                   .getId() + "#comment-" + commentRes.getId();

            UserCommentRes sender = commentRes.getUserCommentResponse();
            NotificationReq notificationReq = NotificationReq.builder()
                                                             .url(url)
                                                             .receiverId(authorCommentParent)
                                                             .senderId(sender.getUserId())
                                                             .message(sender.getDisplayName() +
                                                                              " đã trả lời về bình luận của " +
                                                                              "bạn")
                                                             .build();

            notificationService.createNotification(notificationReq);
            comment = comment.getParent();
        }
    }

    @Override
    public String getPublisherType() {
        return "CommentPublisher";
    }
}
