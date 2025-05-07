package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.NotificationRes;
import OOSE_Final_Project.Blog.entity.Notification;
import OOSE_Final_Project.Blog.mapper.NotificationMapper;
import OOSE_Final_Project.Blog.repository.NotificationRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.INotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public NotificationRes createNotification(NotificationReq notificationReq) {
        Notification notification = new Notification();
        notificationMapper.updateNotificationFromDto(notificationReq, notification);
        notification.setRead(false); // Mặc định thông báo mới chưa đọc

        return changeToRes(notificationRepository.save(notification));
    }

    @Override
    public List<NotificationRes> getAllNotifications() {
        var notifications = notificationRepository.findAll();
        return changeToRes(notifications);

    }

    @Override
    public NotificationRes getNotificationById(Long id) {
        var notification = notificationRepository.findById(id)
                                                 .orElseThrow(() -> new RuntimeException("Notification not found"));
        return changeToRes(notification);
    }

    @Override
    public List<NotificationRes> getNotificationsByUserId(Long userId) {
        var notifications = notificationRepository.findByReceiverId(userId);
        return changeToRes(notifications);
    }

    @Override
    public List<NotificationRes> getUnreadNotificationsByUserId(Long userId) {
        var notifications = notificationRepository.findByReceiverIdAndIsReadFalse(userId);
        return changeToRes(notifications);
    }

    @Override
    public NotificationRes markNotificationAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                                                          .orElseThrow(() -> new IllegalArgumentException(
                                                                  "Notification not found with id: " + id));
        notification.setRead(true);
        notification = notificationRepository.save(notification);
        return changeToRes(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new IllegalArgumentException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    public List<NotificationRes> changeToRes(List<Notification> notifications) {
        return notifications.stream()
                            .map(notification ->
                                 {
                                     NotificationRes notificationRes = new NotificationRes();
                                     notificationMapper.updateNotificationResponseFromEntity(
                                             notification, notificationRes
                                     );
                                     return notificationRes;
                                 }
                            )
                            .collect(Collectors.toList());
    }

    public NotificationRes changeToRes(Notification notification) {
        NotificationRes notificationRes = new NotificationRes();
        notificationMapper.updateNotificationResponseFromEntity(
                notification, notificationRes
        );
        return notificationRes;
    }
}
