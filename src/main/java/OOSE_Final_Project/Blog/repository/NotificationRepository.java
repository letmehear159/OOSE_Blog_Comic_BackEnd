package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findByReceiverId(Long userId);

    List<Notification> findByReceiverIdAndIsReadFalse(Long userId);
}
