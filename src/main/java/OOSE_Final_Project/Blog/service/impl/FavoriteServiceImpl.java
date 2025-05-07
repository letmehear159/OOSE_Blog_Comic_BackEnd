package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.req.FavoriteReq;
import OOSE_Final_Project.Blog.entity.Favorite;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.FavoriteRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.IFavoriteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class FavoriteServiceImpl implements IFavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Favorite createFavorite(FavoriteReq favoriteReq) {
        Favorite favorite = new Favorite();
        Blog blog = blogRepository.findById(favoriteReq.getBlogId())
                                  .orElseThrow(() -> new IllegalArgumentException("Blog not found"));

        favorite.setBlog(blog);

        User user = userRepository.findById(favoriteReq.getUserId())
                                  .orElseThrow(() -> new IllegalArgumentException("User not found"));

        favorite.setUser(user);

        // Kiểm tra xem favorite đã tồn tại với userId và blogId
        Optional<Favorite> existingFavorite = favoriteRepository.findByUserIdAndBlogId(
                favorite.getUser()
                        .getId(), favorite.getBlog()
                                          .getId());

        if (existingFavorite.isPresent()) {
            throw new IllegalArgumentException("Favorite already exists for user " +
                                                       favorite.getUser()
                                                               .getId() + " and blog " + favorite.getBlog()
                                                                                                 .getId());
        }
        return favoriteRepository.save(favorite);
    }

    @Override
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }


    @Override
    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    @Override
    public List<Favorite> getFavoritesByBlogId(Long blogId) {
        return favoriteRepository.findByBlogId(blogId);
    }

    @Override
    public Optional<Favorite> getFavoriteByUserIdAndBlogId(Long userId, Long blogId) {
        return favoriteRepository.findByUserIdAndBlogId(userId, blogId);
    }

    @Override
    public void deleteFavorite(Long id) {
        if (!favoriteRepository.existsById(id)) {
            throw new IllegalArgumentException("Favorite not found with id: " + id);
        }
        favoriteRepository.deleteById(id);
    }


}
