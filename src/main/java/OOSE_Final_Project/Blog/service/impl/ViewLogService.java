package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.entity.DailyViewProjection;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.ViewLog;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.repository.ViewLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ViewLogService {

    @Autowired
    private ViewLogRepository viewLogRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;


    public void logView(Long blogId, Long userId) {
        Blog blog = blogRepository.findById(blogId)
                                  .orElseThrow(() -> new RuntimeException("Blog not found"));

        ViewLog log = new ViewLog();
        log.setBlog(blog);
        if (userId != null) {
            User user = userRepository.findById(userId)
                                      .orElseThrow(() -> new RuntimeException("User not found"));
            log.setViewer(user);

        }
        viewLogRepository.save(log);
    }

    public List<DailyViewProjection> getLast5DaysViews() {
        LocalDate fiveDaysAgo = LocalDate.now()
                                         .minusDays(4); // tính luôn hôm nay
        return viewLogRepository.findDailyViewsFromDate(fiveDaysAgo);
    }
}
