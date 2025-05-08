package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.blog.BlogCharacterReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;
import OOSE_Final_Project.Blog.facade.BlogFacade;
import OOSE_Final_Project.Blog.service.IBlogCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/blog-character")
@RestController
public class BlogCharacterController {

    @Autowired
    IBlogCharacterService blogCharacterService;

    @Autowired
    BlogFacade blogFacade;

    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public ApiResponse<BlogCharacterRes> save(
            @RequestPart("blogCharacterRequest") BlogCharacterReq blogCharacterRequest,
            @RequestPart("thumbnail") MultipartFile thumbnail) throws IOException {
        var result = blogFacade.saveBlogCharacter(blogCharacterRequest, thumbnail);
        return new ApiResponse<>(HttpStatus.CREATED, "Create a blog character", result, null);
    }

    @GetMapping("")
    public ApiResponse<List<BlogCharacterRes>> getAllBlogCharacter() {
        var result = blogCharacterService.findAll();
        return new ApiResponse<>(HttpStatus.CREATED, "Get all blog", result, null);
    }

    @PutMapping("/{blogId}")
    public ApiResponse<BlogCharacterRes> update(
            @PathVariable Long blogId, @RequestPart("blogCharacterRequest") BlogCharacterReq blogCharacterRequest,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) throws IOException {
        var result = blogCharacterService.update(blogId, blogCharacterRequest,thumbnail);
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

    @GetMapping("/related-characters/{comicId}")
    public ApiResponse<List<BlogCharacterRes>> getRelatedCharacters(@PathVariable String comicId) {
        var result = blogCharacterService.getRelatedCharacters(Long.valueOf(comicId));
        return new ApiResponse<>(HttpStatus.CREATED, "Get blog character related to this character", result, null);
    }
}
