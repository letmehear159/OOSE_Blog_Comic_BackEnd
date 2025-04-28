package OOSE_Final_Project.Blog.entity.report;

import OOSE_Final_Project.Blog.entity.BaseEntity;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.enums.EReportType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class ReportBase extends BaseEntity {

    String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User reporter;

    String reason;

    String url;

    EReportType type;

    boolean isRead;

    boolean isHandled;
}
