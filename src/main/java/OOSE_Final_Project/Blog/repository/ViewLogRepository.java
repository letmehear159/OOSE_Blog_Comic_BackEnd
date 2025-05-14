package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.DailyViewProjection;
import OOSE_Final_Project.Blog.entity.ViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ViewLogRepository extends JpaRepository<ViewLog, Long> {
    @Query("SELECT DATE(v.createdAt) AS date, COUNT(v.id) AS totalViews " +
            "FROM ViewLog v " +
            "WHERE v.createdAt >= :startDate " +
            "GROUP BY DATE(v.createdAt) " +
            "ORDER BY DATE(v.createdAt) DESC")
    List<DailyViewProjection> findDailyViewsFromDate(@Param("startDate") LocalDate startDate);


    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
