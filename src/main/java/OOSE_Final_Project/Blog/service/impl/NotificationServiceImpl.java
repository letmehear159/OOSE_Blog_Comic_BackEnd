package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.entity.Notification;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.repository.NotificationRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Notification createNotification(NotificationReq notificationReq) {
        Notification notification = new Notification();
        User receiver = userRepository.findById(notificationReq.getReceiverId())
                                      .orElseThrow(() -> new RuntimeException("User not found"));
        notification.setReceiver(receiver);
        notification.setUrl(notificationReq.getUrl());
        notification.setMessage(notificationReq.getMessage());
        notification.setRead(false); // Mặc định thông báo mới chưa đọc

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByReceiverId(userId);
    }

    @Override
    public List<Notification> getUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.findByReceiverIdAndIsReadFalse(userId);
    }

    @Override
    public Notification markNotificationAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                                                          .orElseThrow(() -> new IllegalArgumentException(
                                                                  "Notification not found with id: " + id));
        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new IllegalArgumentException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }
}
