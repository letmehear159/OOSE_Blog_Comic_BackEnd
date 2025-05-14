package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.FollowReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.entity.Follow;
import OOSE_Final_Project.Blog.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        var result = followService.getAllFollows();
        return new ApiResponse<>(HttpStatus.OK, "Get all follows", result, null);
    }

    @DeleteMapping("")
    public ApiResponse<Boolean> unfollow(@RequestParam long userId, @RequestParam long bloggerId) {
        FollowReq followReq = new FollowReq();
        followReq.setUserId(userId);
        followReq.setBloggerId(bloggerId);
        followService.deleteFollow(followReq);
        return new ApiResponse<>(HttpStatus.OK, "Unfollow a blogger", Boolean.TRUE, null);
    }

    @GetMapping("/find")
    public ApiResponse<Boolean> find(@RequestParam long userId, @RequestParam long bloggerId) {
        followService.getFollowByUserIdAndBloggerId(userId, bloggerId)
                     .orElseThrow();
        return new ApiResponse<>(
                HttpStatus.OK, "Find a blogger", Boolean.TRUE, null);
    }
}
