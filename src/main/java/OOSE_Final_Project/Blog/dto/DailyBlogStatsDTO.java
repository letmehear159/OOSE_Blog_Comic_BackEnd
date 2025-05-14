package OOSE_Final_Project.Blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyBlogStatsDTO {

    private LocalDate date;

    private long blogs;

    private long rates;

    private long comments;

    private long reactions;

    private long views;

}
