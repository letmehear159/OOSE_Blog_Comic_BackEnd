package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "views")
@Entity
public class View extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "blog_id")
    Blog blog;

    long count;

}
