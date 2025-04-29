package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.FollowReq;
import OOSE_Final_Project.Blog.entity.Follow;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.repository.FollowRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowServiceImpl implements IFollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Follow createFollow(FollowReq followReq) {

        Follow follow = new Follow();
        User user = userRepository.findById(followReq.getUserId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        User blogger = userRepository.findById(followReq.getBloggerId())
                                     .orElseThrow(() -> new RuntimeException("Blogger not found"));

        follow.setUser(user);
        follow.setBlogger(blogger);

        // Kiểm tra xem follow đã tồn tại với userId và bloggerId
        Optional<Follow> existingFollow = followRepository.findByUserIdAndBloggerId(
                follow.getUser()
                      .getId(), follow.getBlogger()
                                      .getId());

        if (existingFollow.isPresent()) {
            throw new IllegalArgumentException("Follow already exists for user " +
                                                       follow.getUser()
                                                             .getId() + " and blogger " + follow.getBlogger()
                                                                                                .getId());
        }
        // Kiểm tra không cho phép tự follow
        if (follow.getUser()
                  .getId()
                  .equals(follow.getBlogger()
                                .getId())) {
            throw new IllegalArgumentException("User cannot follow themselves");
        }
        return followRepository.save(follow);
    }

    @Override
    public List<Follow> getAllFollows() {
        return followRepository.findAll();
    }

    @Override
    public Optional<Follow> getFollowById(Long id) {
        return followRepository.findById(id);
    }

    @Override
    public List<Follow> getFollowsByUserId(Long userId) {
        return followRepository.findByUserId(userId);
    }

    @Override
    public List<Follow> getFollowsByBloggerId(Long bloggerId) {
        return followRepository.findByBloggerId(bloggerId);
    }

    @Override
    public Optional<Follow> getFollowByUserIdAndBloggerId(Long userId, Long bloggerId) {
        return followRepository.findByUserIdAndBloggerId(userId, bloggerId);
    }

    @Override
    public void deleteFollow(FollowReq followReq) {
        Follow follow = followRepository.findByUserIdAndBloggerId(followReq.getUserId(), followReq.getBloggerId())
                                        .orElseThrow(() -> new RuntimeException("Follow not found"));
        followRepository.delete(follow);
    }
}
