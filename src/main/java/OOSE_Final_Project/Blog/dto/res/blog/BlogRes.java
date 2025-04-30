package OOSE_Final_Project.Blog.dto.res.blog;

import OOSE_Final_Project.Blog.dto.res.user.AuthorBlogRes;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRes {

    long id;

    String title;

    String content;

    EBlogStatus status;

    AuthorBlogRes author;

    String thumbnail;
}
