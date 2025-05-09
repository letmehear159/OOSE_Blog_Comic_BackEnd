package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IBlogService {

    ResultPaginationDTO getBlogWithKeywords(Specification<Blog> specs, Pageable pageable);

    List<BlogRes> getAllBlogs();

}
