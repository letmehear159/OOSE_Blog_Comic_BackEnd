package OOSE_Final_Project.Blog.entity.blog;

import OOSE_Final_Project.Blog.entity.BaseEntity;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.enums.EBlogType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "blogs")
@NoArgsConstructor
@AllArgsConstructor
public abstract class Blog extends BaseEntity {

    String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    String content;

    @ManyToOne
    @JoinColumn(name = "blogger_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    User author;


    EBlogStatus status;

    String thumbnail;

    String introduction;

    @Enumerated(EnumType.STRING)
    private EBlogType type;
}
