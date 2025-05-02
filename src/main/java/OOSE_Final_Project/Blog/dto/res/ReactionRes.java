package OOSE_Final_Project.Blog.dto.res;

import OOSE_Final_Project.Blog.dto.res.user.UserReactionRes;
import OOSE_Final_Project.Blog.enums.EReaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionRes {

    long id;

    EReaction reaction;

    UserReactionRes user;

    Long blogId;

    Long commentId;

}