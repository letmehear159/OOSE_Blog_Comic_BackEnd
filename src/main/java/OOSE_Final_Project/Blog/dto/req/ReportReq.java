package OOSE_Final_Project.Blog.dto.req;

import OOSE_Final_Project.Blog.enums.EReportType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportReq {

    long userId;

    String reason;

    String url;

    @JsonProperty("eReportType")
    EReportType eReportType;

    long blogId;

    long commentId;

    String type;

}
