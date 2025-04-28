package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.NotificationReq;
import OOSE_Final_Project.Blog.entity.Notification;

import java.util.List;
import java.util.Optional;

public interface INotificationService {
    Notification createNotification(NotificationReq notification);

    List<Notification> getAllNotifications();

    Optional<Notification> getNotificationById(Long id);

    List<Notification> getNotificationsByUserId(Long userId);

    List<Notification> getUnreadNotificationsByUserId(Long userId);

    Notification markNotificationAsRead(Long id);

    void deleteNotification(Long id);
}
