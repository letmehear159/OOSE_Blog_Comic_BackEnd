package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.service.IBlogService;
import OOSE_Final_Project.Blog.specification.BlogSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

    @Autowired
    IBlogService blogService;

    @GetMapping("")
    public ApiResponse<ResultPaginationDTO> getAllBlogCharacter(
            @RequestParam("keyword") String keyword,
            Pageable pageable) {
        Specification<Blog> specs = BlogSpecification.containKeyword(keyword);
        var resultPaginationDTO = blogService.getBlogWithKeywords(specs, pageable);
        return new ApiResponse<>(
                HttpStatus.OK, "Get all blog with pagination and specification", resultPaginationDTO, null);
    }

    @GetMapping("/all")
    public ApiResponse<List<BlogRes>> getAllBlog() {
        var result = blogService.getAllBlogs();
        return new ApiResponse<>(HttpStatus.OK, "Get all blogs", result, null);
    }
}
