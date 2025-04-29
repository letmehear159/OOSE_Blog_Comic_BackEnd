package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.FavoriteReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.entity.Favorite;
import OOSE_Final_Project.Blog.service.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {
    @Autowired
    private IFavoriteService favoriteService;

    @PostMapping
    public ApiResponse<Favorite> createFavorite(@RequestBody FavoriteReq favoriteReq) {
        Favorite result = favoriteService.createFavorite(favoriteReq);
        return new ApiResponse<>(HttpStatus.CREATED, "Create Favorite successfully", result, null);
    }

    @GetMapping
    public ApiResponse<List<Favorite>> getAllFavorites() {
        List<Favorite> favorites = favoriteService.getAllFavorites();
        return new ApiResponse<>(HttpStatus.OK, "Get all favorites", favorites, null);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Favorite>> getFavoritesByUserId(@PathVariable Long userId) {
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(userId);
        return new ApiResponse<>(HttpStatus.OK, "Get favorites by userId", favorites, null);
    }

    @GetMapping("/blog/{blogId}")
    public ApiResponse<List<Favorite>> getFavoritesByBlogId(@PathVariable Long blogId) {
        List<Favorite> favorites = favoriteService.getFavoritesByBlogId(blogId);
        return new ApiResponse<>(HttpStatus.OK, "Get favorites by blogId", favorites, null);
    }

    @GetMapping("/user/{userId}/blog/{blogId}")
    public ApiResponse<Favorite> getFavoriteByUserIdAndBlogId(@PathVariable Long userId, @PathVariable Long blogId) {
        Optional<Favorite> favorite = favoriteService.getFavoriteByUserIdAndBlogId(userId, blogId);
        return favorite
                .map(f -> new ApiResponse<>(HttpStatus.OK, "Found favorite", f, null))
                .orElseGet(() -> new ApiResponse<>(HttpStatus.NOT_FOUND, "Favorite not found", null, "No favorite with given userId and blogId"));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT, "Favorite deleted successfully", null, null);
    }
}
