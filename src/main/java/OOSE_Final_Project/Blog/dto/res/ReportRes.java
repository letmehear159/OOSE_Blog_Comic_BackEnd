package OOSE_Final_Project.Blog.dto.res;

import OOSE_Final_Project.Blog.dto.res.blog.BlogReportRes;
import OOSE_Final_Project.Blog.dto.res.comment.CommentReportRes;
import OOSE_Final_Project.Blog.dto.res.user.UserReportRes;
import OOSE_Final_Project.Blog.enums.EReportType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRes {

    long id;

    UserReportRes reporterRes;

    String reason;

    String url;

    EReportType violationType;

    boolean read;

    boolean handled;

    CommentReportRes commentRes;

    BlogReportRes blogRes;

    String reportType;
}
