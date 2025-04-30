package OOSE_Final_Project.Blog.dto.res.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserReactionRes {

    private long userId;

    private String displayName;
}
