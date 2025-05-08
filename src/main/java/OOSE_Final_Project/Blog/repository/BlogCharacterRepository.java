package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.blog.BlogCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCharacterRepository extends JpaRepository<BlogCharacter, Long> {

    List<BlogCharacter> findByComicId(Long comicId);

}
