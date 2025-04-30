package OOSE_Final_Project.Blog.dto.req.blog;

import OOSE_Final_Project.Blog.entity.Character;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogCharacterReq extends BlogReq{

    @Nullable
    Long comicId;

    Character character;
}
