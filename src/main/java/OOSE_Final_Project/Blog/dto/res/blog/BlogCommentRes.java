package OOSE_Final_Project.Blog.dto.res.blog;

import OOSE_Final_Project.Blog.enums.EBlogType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogCommentRes {
    EBlogType blogType;

    long id;
}
