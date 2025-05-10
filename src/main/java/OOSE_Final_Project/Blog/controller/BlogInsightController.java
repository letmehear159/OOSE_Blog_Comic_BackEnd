package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.blog.BlogInsightReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.blog.BlogInsightRes;
import OOSE_Final_Project.Blog.observer.BlogViewLevelPublisher;
import OOSE_Final_Project.Blog.service.IBlogInsightService;
import OOSE_Final_Project.Blog.service.IViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/blog-insight")
@RestController
public class BlogInsightController {

    @Autowired
    IBlogInsightService blogInsightService;

    @Autowired
    private IViewService viewService;

    @Autowired
    private BlogViewLevelPublisher publisher;

    @PostMapping("")
    public ApiResponse<BlogInsightRes> save(
            @RequestPart("blogInsightReq") BlogInsightReq blogInsightReq,
            @RequestPart("thumbnail") MultipartFile thumbnail) throws IOException {
        var result = blogInsightService.save(blogInsightReq, thumbnail);
        return new ApiResponse<>(HttpStatus.CREATED, "Create a blog insight", result, null);
    }

    @GetMapping("")
    public ApiResponse<List<BlogInsightRes>> getAllBlogCharacter() {
        var result = blogInsightService.findAll();
        return new ApiResponse<>(HttpStatus.CREATED, "Get all blog insight", result, null);
    }

    @PutMapping("/{blogId}")
    public ApiResponse<BlogInsightRes> update(
            @PathVariable String blogId, @RequestPart("blogInsightReq") BlogInsightReq blogComicReq,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) throws IOException {
        var result = blogInsightService.update(Long.valueOf(blogId), blogComicReq, thumbnail);
        return new ApiResponse<>(HttpStatus.OK, "Update a blog insight", result, null);
    }

    @DeleteMapping("/{blogId}")
    public ApiResponse<Boolean> delete(@PathVariable Long blogId) {
        blogInsightService.deleteById(blogId);
        return new ApiResponse<>(HttpStatus.OK, "Delete a blog insight", Boolean.TRUE, null);
    }

    @GetMapping("/character/{characterId}")
    public ApiResponse<List<BlogInsightRes>> getAllByCharacterId(@PathVariable Long characterId) {
        var result = blogInsightService.findByCharacterId(characterId);
        return new ApiResponse<>(HttpStatus.CREATED, "Get all blog insight by character ID", result, null);
    }

    @GetMapping("/{blogId}")
    public ApiResponse<BlogInsightRes> getById(@PathVariable Long blogId) {
        var result = blogInsightService.findById(blogId);
        publisher.notifyObservers(result);
        return new ApiResponse<>(HttpStatus.CREATED, "Get all blog insight by character ID", result, null);
    }
}
