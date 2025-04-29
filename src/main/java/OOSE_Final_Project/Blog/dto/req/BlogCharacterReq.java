package OOSE_Final_Project.Blog.dto.req;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BlogCharacterReq {

    @NonNull
    String title;

    long authorId;

    List<Long> categories;

    List<Long> tags;

    @Nullable
    Long comicId;

    @NonNull
    String content;


}
