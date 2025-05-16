package OOSE_Final_Project.Blog.observer;

import OOSE_Final_Project.Blog.constant.Router;
import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.enums.EBlogType;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.IFollowService;
import OOSE_Final_Project.Blog.service.INotificationService;
import OOSE_Final_Project.Blog.service.IUserService;
import OOSE_Final_Project.Blog.util.SecurityUtil;
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

    @Autowired
    private UserRepository userRepository;



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
        var admin = SecurityUtil.getCurrentUserLogin()
                                .orElse(null);
        if (admin == null) {
            return;
        }
        var adminAccount = userRepository.findByUsernameOrEmail(admin)
                                         .orElse(null);
        if (adminAccount == null) {
            return;
        }
        String url = blog.getType() == EBlogType.CHARACTER ? Router.getCharacterBlog(blog.getId()) :
                     Router.getComicBlog(blog.getId());

        NotificationReq notificationReq = NotificationReq.builder()
                                                         .url(url)
                                                         .receiverId(blog.getAuthor()
                                                                         .getUserId())
                                                         .senderId(adminAccount.getId())
                                                         .message(message)
                                                         .build();
        notificationService.createNotification(notificationReq);
        if (status == EBlogStatus.PUBLISHED) {
            var follows = followService.getFollowersByBloggerId(blog.getAuthor()
                                                                    .getUserId());
            var users = follows.stream()
                               .map(f -> userService.getUserById(f.getUser()
                                                                  .getId()))
                               .toList();

            users.forEach(
                    u -> {

                        NotificationReq notificationReq1 = NotificationReq.builder()
                                                                          .url(url)
                                                                          .senderId(blog.getAuthor()
                                                                                        .getUserId())
                                                                          .receiverId(u.getId())

                                                                          .message("Blogger bạn đang theo dõi " +
                                                                                           "<strong> " +
                                                                                           blog.getAuthor()
                                                                                               .getDisplayName()
                                                                                           +
                                                                                           " </strong> vừa đăng một " +
                                                                                           "bài viết mới")
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
