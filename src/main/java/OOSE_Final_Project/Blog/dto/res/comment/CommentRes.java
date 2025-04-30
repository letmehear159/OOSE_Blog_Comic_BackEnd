package OOSE_Final_Project.Blog.dto.res.comment;

import OOSE_Final_Project.Blog.dto.res.user.UserCommentRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRes {

    long commentId;

    UserCommentRes userCommentResponse;

    String content;

    boolean hasChildComment;
}
