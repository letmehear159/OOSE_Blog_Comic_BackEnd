package OOSE_Final_Project.Blog.entity;

import OOSE_Final_Project.Blog.enums.ERole;
import OOSE_Final_Project.Blog.enums.EUserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private String displayName;
    private String avatar;
    private ERole role;
    private EUserStatus accountStatus;
    private boolean isVerified;
    private long level;



}
