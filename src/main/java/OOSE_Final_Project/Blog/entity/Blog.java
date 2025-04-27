package OOSE_Final_Project.Blog.entity;

import OOSE_Final_Project.Blog.enums.EBlogStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "blogs")
public class Blog extends BaseEntity {

    String title;

    @ManyToOne
    @JoinColumn(name = "blogger_id")
    User author;

    @ManyToMany
    @JoinTable(name = "blog_category", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "category_id"))
    List<Category> categories;

    @ManyToMany
    @JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    List<Tag> tags;

    EBlogStatus status;


}
