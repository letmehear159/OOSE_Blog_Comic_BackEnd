package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.blog.BlogCharacterReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;
import OOSE_Final_Project.Blog.service.IBlogCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/blog-character")
@RestController
public class BlogCharacterController {

    @Autowired
    IBlogCharacterService blogCharacterService;

    @PostMapping("")
    public ApiResponse<BlogCharacterRes> save(@RequestBody BlogCharacterReq blogCharacterRequest) {
        var result = blogCharacterService.save(blogCharacterRequest);
        return new ApiResponse<>(HttpStatus.CREATED, "Create a blog character", result, null);
    }

    @GetMapping("")
    public ApiResponse<List<BlogCharacterRes>> getAllBlogCharacter() {
        var result = blogCharacterService.findAll();
        return new ApiResponse<>(HttpStatus.CREATED, "Get all blog", result, null);
    }

    @PutMapping("/{blogId}")
    public ApiResponse<BlogCharacterRes> update(
            @PathVariable Long blogId, @RequestBody BlogCharacterReq blogCharacterRequest) {
        var result = blogCharacterService.update(blogId, blogCharacterRequest);
        return new ApiResponse<>(HttpStatus.OK, "Update a blog character", result, null);
    }

    @DeleteMapping("/{blogId}")
    public ApiResponse<Boolean> delete(@PathVariable Long blogId) {
        blogCharacterService.deleteById(blogId);
        return new ApiResponse<>(HttpStatus.OK, "Delete a blog character", Boolean.TRUE, null);
    }

    @GetMapping("/{blogId}")
    public ApiResponse<BlogCharacterRes> findById(@PathVariable String blogId) {
        var result = blogCharacterService.findById(Long.valueOf(blogId));
        return new ApiResponse<>(HttpStatus.OK, "Find a blog character", result, null);
    }
}
