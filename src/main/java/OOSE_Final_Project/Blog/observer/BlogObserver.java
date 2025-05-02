package OOSE_Final_Project.Blog.observer;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
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

        NotificationReq notificationReq = NotificationReq.builder()
                                                         .url(null)
                                                         .receiverId(blog.getAuthor()
                                                                         .getUserId())
                                                         .senderId(null)
                                                         .message("Bài viết của bạn vừa được thông qua")
                                                         .build();
        notificationService.createNotification(notificationReq);

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
                                                                                           .getDisplayName() +
                                                                                       " vừa đăng một " +
                                                                                       "Blog mới")
                                                                      .build();
                    notificationService.createNotification(notificationReq1);
                }

        );

    }

    @Override
    public String getPublisherType() {
        return "BlogPublisher";
    }
}
