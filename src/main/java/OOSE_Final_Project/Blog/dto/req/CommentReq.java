package OOSE_Final_Project.Blog.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReq {

    String content;

    long blogId;

    Long parentId = null;

    long userId;
}
