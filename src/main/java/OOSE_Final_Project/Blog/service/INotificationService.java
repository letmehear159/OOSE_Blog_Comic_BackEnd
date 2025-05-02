package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.NotificationRes;

import java.util.List;

public interface INotificationService {
    NotificationRes createNotification(NotificationReq notification);

    List<NotificationRes> getAllNotifications();

    NotificationRes getNotificationById(Long id);

    List<NotificationRes> getNotificationsByUserId(Long userId);

    List<NotificationRes> getUnreadNotificationsByUserId(Long userId);

    NotificationRes markNotificationAsRead(Long id);

    void deleteNotification(Long id);
}
