package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    User receiver;
    String message;
    boolean isRead;
    String url;
}
