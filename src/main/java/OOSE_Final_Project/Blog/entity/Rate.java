package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "rates")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rate extends BaseEntity {

    long rateStar;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    Blog blog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User sender;

}
