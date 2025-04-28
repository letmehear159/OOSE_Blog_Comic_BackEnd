package OOSE_Final_Project.Blog.entity.reaction;

import OOSE_Final_Project.Blog.entity.blog.Blog;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reaction_blogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionBlog extends ReactionBase {

    @ManyToOne
    @JoinColumn(name = "blog_id")
    Blog blog;

}
