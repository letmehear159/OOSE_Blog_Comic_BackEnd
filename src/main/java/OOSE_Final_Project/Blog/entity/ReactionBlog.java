package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reaction_blogs")
public class ReactionBlog extends ReactionBase {

    @ManyToOne
    @JoinColumn(name = "blog_id")
    Blog blog;

}
