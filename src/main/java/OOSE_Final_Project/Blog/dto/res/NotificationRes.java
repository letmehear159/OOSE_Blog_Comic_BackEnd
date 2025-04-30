package OOSE_Final_Project.Blog.dto.res;

import OOSE_Final_Project.Blog.dto.res.user.UserNotificationRes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRes {

    long id;

    UserNotificationRes receiver;

    String message;

    boolean isRead;

    String url;
}
