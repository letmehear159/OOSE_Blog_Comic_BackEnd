package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.dto.res.user.AuthorBlogRes;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.service.IFavoriteService;
import OOSE_Final_Project.Blog.service.IRateService;
import OOSE_Final_Project.Blog.service.IViewService;
import OOSE_Final_Project.Blog.service.strategy.reaction.ReactionStrategyFactory;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BlogMapper {

    @Autowired
    IRateService rateService;

    @Autowired
    IFavoriteService favoriteService;

    @Autowired
    IViewService viewService;

    @Autowired
    ReactionStrategyFactory reactionStrategyFactory;


    @Mapping(target = "author", source = "author", qualifiedByName = "mapAuthorResponse")
    @Mapping(target = "rating", source = "id", qualifiedByName = "mapRating")
    @Mapping(target = "favourite", source = "id", qualifiedByName = "mapFavourite")
    @Mapping(target = "view", source = "id", qualifiedByName = "mapView")
    @Mapping(target = "reaction", source = "id", qualifiedByName = "mapReaction")
    @Mapping(target = "rateCount", source = "id", qualifiedByName = "mapRateCount")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateToBlogResponseFromEntity(
            Blog source, @MappingTarget BlogRes target);


    @Named("mapAuthorResponse")
    AuthorBlogRes mapAuthorResponse(User author) {
        return new AuthorBlogRes.Builder().avatar(author.getAvatar())
                                          .displayName(author.getDisplayName())
                                          .email(author.getEmail())
                                          .level(author.getLevel())
                                          .userId(author.getId())
                                          .build();
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
        if(result==null) {
            return 0;
        }
        return result.getCount();
    }

}
