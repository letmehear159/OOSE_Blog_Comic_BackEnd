package OOSE_Final_Project.Blog.entity.blog;

import OOSE_Final_Project.Blog.entity.Character;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blog_characters")
@Getter
@Setter

public class BlogCharacter extends Blog {

    @Column(columnDefinition = "MEDIUMTEXT")
    String content;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    BlogComic comic;

    @OneToOne()
    @JoinColumn(name = "character_id")
    Character character;



}
