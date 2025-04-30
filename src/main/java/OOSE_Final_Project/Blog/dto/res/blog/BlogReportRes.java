package OOSE_Final_Project.Blog.dto.res.blog;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogReportRes {

    String blogTitle;

    String authorDisplayName;

    String authorAvatar;

    String blogThumbnail;
}
