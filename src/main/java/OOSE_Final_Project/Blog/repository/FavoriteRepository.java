package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<OOSE_Final_Project.Blog.entity.Favorite, Long> {


    Optional<Favorite> findByUserIdAndBlogId(Long id, Long id1);

    List<Favorite> findByUserId(Long userId);

    List<Favorite> findByBlogId(Long blogId);
}
