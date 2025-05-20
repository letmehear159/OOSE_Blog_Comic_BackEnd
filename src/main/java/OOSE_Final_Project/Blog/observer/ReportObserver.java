package OOSE_Final_Project.Blog.observer;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.ReportRes;
import OOSE_Final_Project.Blog.service.INotificationService;
import OOSE_Final_Project.Blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportObserver implements Observer {

    @Autowired
    INotificationService notificationService;

    @Autowired
    IUserService userService;

    @Override
    public void update(Object data) {

        ReportRes res = (ReportRes) data;
        String message;
        if (res.isHandled()) {
            message = "<strong> Admin </strong> đã xử lý bình luận vi phạm mà bạn báo cáo!";
        } else {
            message = "<strong> Admin </strong> đã đã tiếp nhận báo cáo của bạn và đang trong quá trình xử lý!";
        }
        var admin = userService.findByUsernameOrEmail("admin");
        NotificationReq notificationReq = NotificationReq.builder()
                                                         .url(res.getUrl())
                                                         .receiverId(res.getReporterRes()
                                                                        .getId())
                                                         .message(message)
                                                         .senderId(admin.getId())
                                                         .build();

        notificationService.createNotification(notificationReq);
    }

    @Override
    public String getPublisherType() {
        return "ReportPublisher";
    }

}
