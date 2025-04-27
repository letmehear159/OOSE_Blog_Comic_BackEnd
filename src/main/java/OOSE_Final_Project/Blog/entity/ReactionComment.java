package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reaction_comments")
public class ReactionComment extends ReactionBase {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

}
