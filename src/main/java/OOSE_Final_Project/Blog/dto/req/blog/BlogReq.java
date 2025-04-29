package OOSE_Final_Project.Blog.dto.req.blog;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BlogReq {

    @NonNull
    String title;

    long authorId;

    @NonNull
    String content;
}
