package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "otps")
@Getter
@Setter
public class OTP extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String otp;

}
