package OOSE_Final_Project.Blog.dto.res;

import OOSE_Final_Project.Blog.dto.res.user.UserNotificationRes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationRes {

    long id;

    UserNotificationRes sender;

    String message;

    boolean isRead;

    String url;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
