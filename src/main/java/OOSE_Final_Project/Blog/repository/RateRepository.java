package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {


}
