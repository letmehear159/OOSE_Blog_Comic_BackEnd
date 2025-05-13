package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.blog.BlogCharacterReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogCharacterRes;
import OOSE_Final_Project.Blog.dto.res.user.AuthorBlogRes;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.BlogCharacter;
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

@Mapper(componentModel = "spring",
        uses = {UserRepository.class, BlogComicRepository.class, CategoryRepository.class, TagRepository.class})
public abstract class BlogCharacterMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected BlogComicRepository blogComicRepository;

    @Autowired
    IRateService rateService;

    @Autowired
    IFavoriteService favoriteService;

    @Autowired
    IViewService viewService;

    @Autowired
    ReactionStrategyFactory reactionStrategyFactory;


    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "comic", source = "comicId", qualifiedByName = "mapComic")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateBlogCharacterFromDto(BlogCharacterReq source, @MappingTarget BlogCharacter target);

    @Mapping(target = "author", source = "author", qualifiedByName = "mapAuthorResponse")
    //    @Mapping(target = "character", ignore = true)
    @Mapping(target = "comicId", source = "comic", qualifiedByName = "mapComicId")
    @Mapping(target = "rating", source = "id", qualifiedByName = "mapRating")
    @Mapping(target = "favourite", source = "id", qualifiedByName = "mapFavourite")
    @Mapping(target = "view", source = "id", qualifiedByName = "mapView")
    @Mapping(target = "reaction", source = "id", qualifiedByName = "mapReaction")
    @Mapping(target = "rateCount", source = "id", qualifiedByName = "mapRateCount")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateBlogCharacterResponseFromEntityWithoutDetail(
            BlogCharacter source, @MappingTarget BlogCharacterRes target);

    @Mapping(target = "author", source = "author", qualifiedByName = "mapAuthorResponse")
    @Mapping(target = "comicId", source = "comic", qualifiedByName = "mapComicId")
    @Mapping(target = "rating", source = "id", qualifiedByName = "mapRating")
    @Mapping(target = "favourite", source = "id", qualifiedByName = "mapFavourite")
    @Mapping(target = "view", source = "id", qualifiedByName = "mapView")
    @Mapping(target = "reaction", source = "id", qualifiedByName = "mapReaction")
    @Mapping(target = "rateCount", source = "id", qualifiedByName = "mapRateCount")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateBlogCharacterResponseFromEntity(
            BlogCharacter source, @MappingTarget BlogCharacterRes target);

    @Named("mapAuthorResponse")
    AuthorBlogRes mapAuthorResponse(User author) {
        AuthorBlogRes authorBlogRes = new AuthorBlogRes.Builder().avatar(author.getAvatar())
                                                                 .displayName(author.getDisplayName())
                                                                 .email(author.getEmail())
                                                                 .level(author.getLevel())
                                                                 .userId(author.getId())
                                                                 .build();
        return authorBlogRes;
    }

    @Named("mapComicId")
    Long mapComicId(BlogComic comic) {
        return comic.getId();
    }



    @Named("mapAuthor")
    User mapAuthor(Long authorId) {
        return userRepository.findById(authorId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorId));
    }

    @Named("mapComic")
    BlogComic mapComic(Long comicId) {
        if (comicId == null) {
            return null;
        }
        return blogComicRepository.findById(comicId)
                                  .orElseThrow(() -> new IllegalArgumentException(
                                          "BlogComic not found with id:" + comicId));
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