package OOSE_Final_Project.Blog.dto.res.blog;

import OOSE_Final_Project.Blog.dto.res.user.AuthorBlogRes;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.enums.EBlogType;
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

    String introduction;

    EBlogType type;

    double rating;

    long rateCount;

    long favourite;

    long view;

    long reaction;
}
