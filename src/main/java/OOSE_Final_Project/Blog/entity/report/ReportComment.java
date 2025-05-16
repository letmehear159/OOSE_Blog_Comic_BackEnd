package OOSE_Final_Project.Blog.entity.report;

import OOSE_Final_Project.Blog.entity.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Table(name = "report_comments")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportComment extends ReportBase {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Comment comment;

}
