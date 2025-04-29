package OOSE_Final_Project.Blog.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateReq {

    long userId;

    long blogId;

    long rating;
}
