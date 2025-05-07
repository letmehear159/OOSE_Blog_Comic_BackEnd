package OOSE_Final_Project.Blog.dto.res.blog;

import OOSE_Final_Project.Blog.entity.Character;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogCharacterRes extends BlogRes {
    Character character;

    Long comicId;
}
