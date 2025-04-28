package OOSE_Final_Project.Blog.dto;

import OOSE_Final_Project.Blog.enums.EReaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionReq {

    long authorId;

    EReaction reaction;

    long blogId;

    long commentId;

    String type;
}
