package OOSE_Final_Project.Blog.entity;

import OOSE_Final_Project.Blog.enums.EReaction;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class ReactionBase extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    User author;

    EReaction reaction;


}
