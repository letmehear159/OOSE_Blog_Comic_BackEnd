package OOSE_Final_Project.Blog.entity.reaction;

import OOSE_Final_Project.Blog.entity.BaseEntity;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.enums.EReaction;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class ReactionBase extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    User author;

    EReaction reaction;


}
