package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.blog.BlogComicReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogComicRes;
import OOSE_Final_Project.Blog.dto.res.user.AuthorBlogRes;
import OOSE_Final_Project.Blog.entity.Category;
import OOSE_Final_Project.Blog.entity.Tag;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.BlogComic;
import OOSE_Final_Project.Blog.repository.BlogComicRepository;
import OOSE_Final_Project.Blog.repository.CategoryRepository;
import OOSE_Final_Project.Blog.repository.TagRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.IFavoriteService;
import OOSE_Final_Project.Blog.service.IRateService;
import OOSE_Final_Project.Blog.service.IViewService;
import OOSE_Final_Project.Blog.service.strategy.reaction.ReactionStrategyFactory;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {UserRepository.class, BlogComicRepository.class, CategoryRepository.class, TagRepository.class})
public abstract class BlogComicMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected TagRepository tagRepository;

    @Autowired
    IRateService rateService;

    @Autowired
    IFavoriteService favoriteService;

    @Autowired
    IViewService viewService;

    @Autowired
    ReactionStrategyFactory reactionStrategyFactory;

    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategories")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateBlogComicFromDto(BlogComicReq source, @MappingTarget BlogComic target);

    @Mapping(target = "author", source = "author", qualifiedByName = "mapAuthorResponse")
    @Mapping(target = "rating", source = "id", qualifiedByName = "mapRating")
    @Mapping(target = "favourite", source = "id", qualifiedByName = "mapFavourite")
    @Mapping(target = "view", source = "id", qualifiedByName = "mapView")
    @Mapping(target = "reaction", source = "id", qualifiedByName = "mapReaction")
    @Mapping(target = "rateCount", source = "id", qualifiedByName = "mapRateCount")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateBlogComicResponseFromEntity(
            BlogComic source, @MappingTarget BlogComicRes target);

    @Named("mapAuthorResponse")
    AuthorBlogRes mapAuthorResponse(User author) {
        return new AuthorBlogRes.Builder().avatar(author.getAvatar())
                                          .displayName(author.getDisplayName())
                                          .email(author.getEmail())
                                          .level(author.getLevel())
                                          .userId(author.getId())
                                          .build();
    }

    @Named("mapAuthor")
    User mapAuthor(Long authorId) {
        return userRepository.findById(authorId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorId));
    }


    @Named("mapCategories")
    List<Category> mapCategories(List<Long> categoryIds) {
        if (categoryIds == null) {
            return null;
        }
        return categoryRepository.findAllById(categoryIds);
    }

    @Named("mapTags")
    List<Tag> mapTags(List<Long> tagIds) {
        if (tagIds == null) {
            return null;
        }
        return tagRepository.findAllById(tagIds);
    }


    @Named("mapRateCount")
    public long mapRateCount(long blogId) {
        return rateService.getRatesCountForBlogId(blogId);
    }

    @Named("mapRating")
    public double mapRating(long blogId) {
        return rateService.getRatesForBlogId(blogId);
    }

    @Named("mapFavourite")
    public long mapFavourite(long blogId) {
        return favoriteService.getFavoritesByBlogId(blogId)
                              .size();
    }

    @Named("mapReaction")
    public long mapReaction(long blogId) {
        var service = reactionStrategyFactory.getStrategy("Blog");
        var result = service.getAllReactionById(blogId);
        return result.size();
    }

    @Named("mapView")
    public long mapView(long blogId) {
        var result = viewService.getViewByBlogId(blogId);
        if (result == null) {
            return 0;
        }
        return result.getCount();
    }
}