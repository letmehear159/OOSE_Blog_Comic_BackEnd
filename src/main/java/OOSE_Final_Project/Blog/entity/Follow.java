package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "follows")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "blogger_id", nullable = false)
    User blogger;

}
