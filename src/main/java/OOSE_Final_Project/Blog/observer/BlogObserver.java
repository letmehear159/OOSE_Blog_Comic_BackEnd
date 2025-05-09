package OOSE_Final_Project.Blog.observer;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.service.IFollowService;
import OOSE_Final_Project.Blog.service.INotificationService;
import OOSE_Final_Project.Blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlogObserver implements Observer {

    @Autowired
    INotificationService notificationService;

    @Autowired
    IUserService userService;

    @Autowired
    IFollowService followService;

    @Override
    public void update(Object data) {
        BlogRes blog = (BlogRes) data;

        var status = blog.getStatus();
        String message;
        if (status == EBlogStatus.PUBLISHED) {
            message = "Bài viết của bạn vừa được thông qua";
        } else if (status == EBlogStatus.DENIED) {
            message = "Bài viết của bạn bị từ  chối";
        } else {
            message = "Bài viết của bạn bị ẩn khỏi người đọc";
        }
        NotificationReq notificationReq = NotificationReq.builder()
                .url(null)
                .receiverId(blog.getAuthor()
                        .getUserId())
                .senderId(null)
                .message(message)
                .build();
        notificationService.createNotification(notificationReq);
        if (status == EBlogStatus.PUBLISHED) {
            var follows = followService.getFollowsByBloggerId(blog.getAuthor()
                    .getUserId());
            var users = follows.stream()
                    .map(f -> userService.getUserById(f.getUser()
                            .getId()))
                    .toList();

            users.forEach(
                    u -> {
                        NotificationReq notificationReq1 = NotificationReq.builder()
                                .url(null)
                                .senderId(blog.getAuthor()
                                        .getUserId())
                                .receiverId(u.getId())
                                .message("Blogger bạn đang theo dõi " +
                                        blog.getAuthor()
                                                .getDisplayName()
                                        +
                                        " vừa đăng một " +
                                        "Blog mới")
                                .build();
                        notificationService.createNotification(notificationReq1);
                    }

            );
        }

    }

    @Override
    public String getPublisherType() {
        return "BlogPublisher";
    }
}
