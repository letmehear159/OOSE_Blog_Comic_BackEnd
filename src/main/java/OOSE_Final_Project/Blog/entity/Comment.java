package OOSE_Final_Project.Blog.entity;

import OOSE_Final_Project.Blog.entity.blog.Blog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User author;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    Blog blog;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    @JsonIgnore
    Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> children;
}
