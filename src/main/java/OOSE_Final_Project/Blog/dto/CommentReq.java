package OOSE_Final_Project.Blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReq {

    String content;

    long blogId;

    long parentId;

    long userId;
}
