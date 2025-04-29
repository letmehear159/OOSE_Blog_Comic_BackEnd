package OOSE_Final_Project.Blog.entity.blog;

import OOSE_Final_Project.Blog.entity.Category;
import OOSE_Final_Project.Blog.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "blog_comics")
@Getter
@Setter
public class BlogComic extends Blog {

    @Column(columnDefinition = "MEDIUMTEXT")
    String content;

    @ManyToMany
    @JoinTable(name = "blog_category", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "category_id"))
    List<Category> categories;

    @ManyToMany
    @JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    List<Tag> tags;

}
