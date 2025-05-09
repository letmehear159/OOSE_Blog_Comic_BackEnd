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



    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "blog-comic_category", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "category_id"))
    List<Category> categories;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "blog-comic_tag", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    List<Tag> tags;

}
