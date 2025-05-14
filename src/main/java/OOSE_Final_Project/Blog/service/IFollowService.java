package OOSE_Final_Project.Blog.service;

import java.util.List;
import java.util.Optional;

import OOSE_Final_Project.Blog.dto.req.FollowReq;
import OOSE_Final_Project.Blog.entity.Follow;

public interface IFollowService {
    Follow createFollow(FollowReq follow);

    List<Follow> getAllFollows();

    Optional<Follow> getFollowById(Long id);

    List<Follow> getFollowingByUserId(Long userId);

    List<Follow> getFollowersByBloggerId(Long bloggerId);

    void deleteFollow(FollowReq follow);

    Optional<Follow> getFollowByUserIdAndBloggerId(Long userId, Long bloggerId);

}
