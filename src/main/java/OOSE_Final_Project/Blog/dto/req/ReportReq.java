package OOSE_Final_Project.Blog.dto.req;

import OOSE_Final_Project.Blog.enums.EReportType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportReq {
    String content;

    long userId;

    String reason;

    String url;

    EReportType eReportType;

    long blogId;

    long commentId;

    String type;

}
