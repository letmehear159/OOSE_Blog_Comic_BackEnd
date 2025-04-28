package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {


    Optional<Rate> findByUserIdAndBlogId(Long id, Long id1);

    List<Rate> findByBlogId(Long blogId);

    List<Rate> findByUserId(Long userId);
}
