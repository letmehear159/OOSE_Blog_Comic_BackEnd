package OOSE_Final_Project.Blog.entity.blog;

import OOSE_Final_Project.Blog.entity.Category;
import OOSE_Final_Project.Blog.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "blog_insights")
@Getter
@Setter
public class BlogInsight extends Blog {

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "blog-insight_category", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "category_id"))
    List<Category> categories;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "blog-insight_tag", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    List<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    BlogComic comic;

    @ManyToOne
    @JoinColumn(name = "character_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    BlogCharacter blogCharacter;

}

