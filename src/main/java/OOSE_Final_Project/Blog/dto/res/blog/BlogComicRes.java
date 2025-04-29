package OOSE_Final_Project.Blog.dto.res.blog;

import OOSE_Final_Project.Blog.entity.Category;
import OOSE_Final_Project.Blog.entity.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BlogComicRes extends BlogRes{
    List<Category> categories;

    List<Tag> tags;


}
