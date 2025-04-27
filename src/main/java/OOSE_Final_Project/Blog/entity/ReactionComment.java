package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reaction_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionComment extends ReactionBase {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

}
