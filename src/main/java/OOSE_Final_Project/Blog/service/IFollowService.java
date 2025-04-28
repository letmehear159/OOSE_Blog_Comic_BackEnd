package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.FollowReq;
import OOSE_Final_Project.Blog.entity.Follow;

import java.util.List;
import java.util.Optional;

public interface IFollowService {
    Follow createFollow(FollowReq follow);

    List<Follow> getAllFollows();

    Optional<Follow> getFollowById(Long id);

    List<Follow> getFollowsByUserId(Long userId);

    List<Follow> getFollowsByBloggerId(Long bloggerId);

    Optional<Follow> getFollowByUserIdAndBloggerId(Long userId, Long bloggerId);

    void deleteFollow(Long id);
}
