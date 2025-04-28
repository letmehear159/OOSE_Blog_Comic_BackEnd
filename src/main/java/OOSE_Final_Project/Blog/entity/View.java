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

@Table(name = "views")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class View extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "blog_id")
    Blog blog;

    long count;

}
