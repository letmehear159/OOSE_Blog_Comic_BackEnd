package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.DailyBlogStatsDTO;
import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IBlogService {

    ResultPaginationDTO getBlogWithKeywords(Specification<Blog> specs, Pageable pageable);

    List<BlogRes> getAllBlogs();

    ResultPaginationDTO getBlogsWithFilterAndPageable(List<Long> categoryIds, List<Long> tagIds, Pageable pageable);

    BlogRes updateBlogStatus(Long id, EBlogStatus status);

    BlogRes getBlogById(Long id);

    ResultPaginationDTO getBlogsWithPageable(Pageable pageable);

    public long getTodayBlogCount();

    long getAllBlogsCount();

    public List<DailyBlogStatsDTO> getLast5DaysStats();

    void deleteBlogById(Long id);
}
