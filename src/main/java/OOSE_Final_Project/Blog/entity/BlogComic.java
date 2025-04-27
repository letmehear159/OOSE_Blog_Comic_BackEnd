package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blog_comics")
@Getter
@Setter
public class BlogComic extends Blog {

    @Column(columnDefinition = "MEDIUMTEXT")
    String content;


}
