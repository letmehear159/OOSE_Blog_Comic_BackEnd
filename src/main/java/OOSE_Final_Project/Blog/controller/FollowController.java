package OOSE_Final_Project.Blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import OOSE_Final_Project.Blog.dto.req.FollowReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.entity.Follow;
import OOSE_Final_Project.Blog.service.IFollowService;

@RequestMapping("api/v1/follows")
@RestController
public class FollowController {

    @Autowired
    IFollowService followService;

    @PostMapping("")
    public ApiResponse<Follow> follow(@RequestBody FollowReq followReq) {
        var result = followService.createFollow(followReq);
        return new ApiResponse<>(HttpStatus.CREATED, "Follow a blogger", result, null);
    }

    @GetMapping("")
    public ApiResponse<List<Follow>> getAllFollows() {
        var result=followService.getAllFollows();
        return new ApiResponse<>(HttpStatus.OK, "Get all follows", result, null);
    }

    @DeleteMapping("")
    public ApiResponse<Boolean> unfollow(@RequestBody FollowReq followReq) {
        followService.deleteFollow(followReq);
        return new ApiResponse<>(HttpStatus.OK,"Unfollow a blogger",Boolean.TRUE, null);
    }

    /**
     * Lấy danh sách blogger mà một người dùng đang theo dõi
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Follow>> getFollowingByUser(@PathVariable Long userId) {
        var result = followService.getFollowingByUserId(userId);
        return new ApiResponse<>(HttpStatus.OK, "Get all bloggers followed by user", result, null);
    }
    
    /**
     * Lấy danh sách người dùng đang theo dõi một blogger
     */
    @GetMapping("/blogger/{bloggerId}")
    public ApiResponse<List<Follow>> getFollowersByBlogger(@PathVariable Long bloggerId) {
        var result = followService.getFollowersByBloggerId(bloggerId);
        return new ApiResponse<>(HttpStatus.OK, "Get all followers of blogger", result, null);
    }
}
