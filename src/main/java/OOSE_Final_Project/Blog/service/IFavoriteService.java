package OOSE_Final_Project.Blog.service;

import OOSE_Final_Project.Blog.dto.req.FavoriteReq;
import OOSE_Final_Project.Blog.entity.Favorite;

import java.util.List;
import java.util.Optional;

public interface IFavoriteService {
    Favorite createFavorite(FavoriteReq favorite);

    List<Favorite> getAllFavorites();

    Optional<Favorite> getFavoriteById(Long id);

    List<Favorite> getFavoritesByUserId(Long userId);

    List<Favorite> getFavoritesByBlogId(Long blogId);

    Optional<Favorite> getFavoriteByUserIdAndBlogId(Long userId, Long blogId);

    void deleteFavorite(Long id);
}
