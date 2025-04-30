package OOSE_Final_Project.Blog.dto.req;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserReq {

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String displayName;

    @Nullable
    private String avatar;

}
