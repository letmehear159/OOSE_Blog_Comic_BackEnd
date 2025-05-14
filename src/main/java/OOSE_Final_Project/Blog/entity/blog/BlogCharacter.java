package OOSE_Final_Project.Blog.entity.blog;

import OOSE_Final_Project.Blog.entity.Character;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "blog_characters")
@Getter
@Setter

public class BlogCharacter extends Blog {


    @ManyToOne
    @JoinColumn(name = "comic_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    BlogComic comic;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Character character;


}
