package OOSE_Final_Project.Blog.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationReq {

    @NonNull
    Long receiverId;

    String message;

    String url;
}
