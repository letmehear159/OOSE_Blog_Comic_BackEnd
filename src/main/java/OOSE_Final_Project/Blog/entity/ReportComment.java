package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "report_comments")
@Entity
public class ReportComment extends ReportBase {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

}
