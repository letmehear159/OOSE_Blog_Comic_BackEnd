package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {

}
