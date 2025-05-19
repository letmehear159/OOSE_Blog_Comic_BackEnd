package OOSE_Final_Project.Blog.entity;

import OOSE_Final_Project.Blog.entity.blog.Blog;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Table(name = "rates")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rate extends BaseEntity {

    double rateStar;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Blog blog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

}
