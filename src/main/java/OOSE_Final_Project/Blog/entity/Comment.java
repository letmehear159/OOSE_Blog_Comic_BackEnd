package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    Blog blog;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Comment> children;





}
