package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.NotificationRes;
import OOSE_Final_Project.Blog.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private INotificationService notificationService;

    // Lấy tất cả thông báo của user
    @GetMapping("/user/{userId}")
    public ApiResponse<List<NotificationRes>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationRes> result = notificationService.getNotificationsByUserId(userId);
        return new ApiResponse<>(HttpStatus.OK, "Fetched all notifications", result, null);
    }

    // Lấy thông báo chưa đọc của user
    @GetMapping("/user/{userId}/unread")
    public ApiResponse<List<NotificationRes>> getUnreadNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationRes> result = notificationService.getUnreadNotificationsByUserId(userId);
        return new ApiResponse<>(HttpStatus.OK, "Fetched unread notifications", result, null);
    }

    // Đánh dấu thông báo là đã đọc
    @PutMapping("/{id}/read")
    public ApiResponse<NotificationRes> markNotificationAsRead(@PathVariable Long id) {
        NotificationRes updated = notificationService.markNotificationAsRead(id);
        return new ApiResponse<>(HttpStatus.OK, "Marked as read", updated, null);
    }

    // Xoá thông báo
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT, "Notification deleted", Boolean.TRUE, null);
    }
}
