package OOSE_Final_Project.Blog.entity;

import java.time.LocalDate;

public interface DailyViewProjection {
    LocalDate getDate();
    Long getTotalViews();
}
