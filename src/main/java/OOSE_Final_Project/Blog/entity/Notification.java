package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver;

    String message;

    boolean isRead;

    String url;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    User sender;
}
