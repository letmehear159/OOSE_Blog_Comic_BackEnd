package OOSE_Final_Project.Blog.dto.req;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class RateReq {

    @NonNull
    long userId;

    @NonNull
    long blogId;

    @NonNull
    long rateStar;
}
