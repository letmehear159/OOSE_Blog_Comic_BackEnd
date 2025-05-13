package OOSE_Final_Project.Blog.dto.res.comment;

import OOSE_Final_Project.Blog.dto.res.user.UserCommentRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRes {

    long id;

    UserCommentRes userCommentResponse;

    String content;

    boolean hasChildComment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    List<CommentRes> children;
}
