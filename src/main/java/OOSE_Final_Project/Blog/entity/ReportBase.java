package OOSE_Final_Project.Blog.entity;

import OOSE_Final_Project.Blog.enums.EReportType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class ReportBase extends BaseEntity {

    String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User reporter;

    String reason;

    String url;

    EReportType type;
}
