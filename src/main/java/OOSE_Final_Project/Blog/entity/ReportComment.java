package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "report_comments")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportComment extends ReportBase {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

}
