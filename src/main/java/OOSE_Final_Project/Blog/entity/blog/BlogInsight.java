package OOSE_Final_Project.Blog.entity.blog;

import OOSE_Final_Project.Blog.entity.Category;
import OOSE_Final_Project.Blog.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "blog_insights")
@Getter
@Setter
public class BlogInsight extends Blog {

    @Column(columnDefinition = "MEDIUMTEXT")
    String content;

    @ManyToMany
    @JoinTable(name = "blog-insight_category", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "category_id"))
    List<Category> categories;

    @ManyToMany
    @JoinTable(name = "blog-insight_tag", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns =
    @JoinColumn(name = "tag_id"))
    List<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    BlogComic comic;

    @ManyToOne
    @JoinColumn(name = "character_id")
    BlogCharacter blogCharacter;

}

