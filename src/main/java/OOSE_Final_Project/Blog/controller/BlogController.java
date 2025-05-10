package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.enums.EBlogStatus;
import OOSE_Final_Project.Blog.observer.BlogPublisher;
import OOSE_Final_Project.Blog.service.IBlogService;
import OOSE_Final_Project.Blog.specification.BlogSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

    @Autowired
    IBlogService blogService;

    @Autowired
    BlogPublisher blogPublisher;


    @GetMapping("/search")
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

    @GetMapping("/filter")
    public ApiResponse<ResultPaginationDTO> searchByCat(
            @RequestParam List<Long> categoryIds,
            @RequestParam List<Long> tagIds,
            Pageable pageable
    ) {
        var result = blogService.getBlogsWithFilterAndPageable(categoryIds, tagIds, pageable);
        return new ApiResponse<>(HttpStatus.OK, "Get all blogs with filter and pagination", result, null);
    }

    @PatchMapping("/review/{id}")
    public ApiResponse<BlogRes> updateBlogStatus(
            @PathVariable Long id, @RequestBody
            EBlogStatus status) {
        var result = blogService.updateBlogStatus(id, status);
        blogPublisher.notifyObservers(result);
        return new ApiResponse<>(HttpStatus.OK, "Update status of blogs", result, null);
    }

    @GetMapping("/{id}")
    public ApiResponse<BlogRes> getBlogType(
            @PathVariable Long id
    ) {
        var result = blogService.getBlogById(id);
        return new ApiResponse<>(HttpStatus.OK, "Get blog type with id", result, null);
    }
}
