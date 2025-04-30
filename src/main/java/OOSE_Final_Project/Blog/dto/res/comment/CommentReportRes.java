package OOSE_Final_Project.Blog.dto.res.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentReportRes {
    String content;

    String userDisplayName;

    String avatar;
}
