package OOSE_Final_Project.Blog.dto.req.blog;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BlogComicReq extends BlogReq{
    List<Long> categories;

    List<Long> tags;
}
