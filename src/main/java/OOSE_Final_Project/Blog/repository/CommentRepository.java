package OOSE_Final_Project.Blog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<OOSE_Final_Project.Blog.entity.Comment, Long> {


}
