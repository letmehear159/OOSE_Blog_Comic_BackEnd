package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.ResultPaginationDTO;
import OOSE_Final_Project.Blog.dto.req.blog.BlogComicReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.blog.BlogComicRes;
import OOSE_Final_Project.Blog.observer.BlogViewLevelPublisher;
import OOSE_Final_Project.Blog.service.IBlogComicService;
import OOSE_Final_Project.Blog.service.IViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/blog-comic")
@RestController
public class BlogComicController {

    @Autowired
    IBlogComicService blogComicService;


    @Autowired
    private IViewService viewService;

    @Autowired
    private BlogViewLevelPublisher publisher;

    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public ApiResponse<BlogComicRes> save(
            @RequestPart("blogComicRequest") BlogComicReq blogComicReq,
            @RequestPart("thumbnail") MultipartFile thumbnail) throws IOException {
        var result = blogComicService.save(blogComicReq, thumbnail);
        return new ApiResponse<>(HttpStatus.CREATED, "Create a blog comic", result, null);
    }

    @GetMapping("/all")
    public ApiResponse<List<BlogComicRes>> getAllBlogCharacter() {
        var result = blogComicService.findAll();
        return new ApiResponse<>(HttpStatus.CREATED, "Get all blog", result, null);
    }

    @GetMapping("/{blogId}")
    public ApiResponse<BlogComicRes> getBlogCharacterById(@PathVariable Long blogId) {
        var result = blogComicService.findById(blogId);
        publisher.notifyObservers(result);
        return new ApiResponse<>(HttpStatus.CREATED, "Get bloc comic by Id", result, null);
    }

    @PutMapping("/{blogId}")
    public ApiResponse<BlogComicRes> update(
            @PathVariable String blogId, @RequestPart("blogComicReq") BlogComicReq blogComicReq,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) throws IOException {
        var result = blogComicService.update(Long.valueOf(blogId), blogComicReq, thumbnail);
        return new ApiResponse<>(HttpStatus.OK, "Update a blog comic", result, null);
    }

    @DeleteMapping("/{blogId}")
    public ApiResponse<Boolean> delete(@PathVariable Long blogId) {
        blogComicService.deleteById(blogId);
        return new ApiResponse<>(HttpStatus.OK, "Delete a blog comic", Boolean.TRUE, null);
    }


    @GetMapping("")
    public ApiResponse<ResultPaginationDTO> getBlogWithPagination(Pageable pageable) {
        var result = blogComicService.findAll(pageable);
        return new ApiResponse<>(HttpStatus.OK, "Get all blog comic with pagination", result, null);
    }

}
