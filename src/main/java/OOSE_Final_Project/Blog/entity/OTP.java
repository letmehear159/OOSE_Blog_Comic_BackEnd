package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "otps")
public class OTP extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String otp;

}
