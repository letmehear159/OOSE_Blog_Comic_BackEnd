package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {


    Optional<Follow> findByUserIdAndBloggerId(Long id, Long id1);

    List<Follow> findByUserId(Long userId);

    List<Follow> findByBloggerId(Long bloggerId);
}
