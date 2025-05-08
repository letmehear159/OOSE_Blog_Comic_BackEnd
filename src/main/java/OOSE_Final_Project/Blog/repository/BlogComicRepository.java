package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.blog.BlogComic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogComicRepository extends JpaRepository<BlogComic, Long>, JpaSpecificationExecutor<BlogComic> {


    Optional<BlogComic> findById(Long comicId);
}
