package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "report_blogs")
@Entity
public class ReportBlog extends ReportBase {

    @ManyToOne
    @JoinColumn(name = "blog_id")
    Blog blog;
}
