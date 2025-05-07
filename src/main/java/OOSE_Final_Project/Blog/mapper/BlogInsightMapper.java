package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.blog.BlogInsightReq;
import OOSE_Final_Project.Blog.dto.res.blog.BlogInsightRes;
import OOSE_Final_Project.Blog.dto.res.user.AuthorBlogRes;
import OOSE_Final_Project.Blog.entity.Category;
import OOSE_Final_Project.Blog.entity.Tag;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.BlogCharacter;
import OOSE_Final_Project.Blog.entity.blog.BlogComic;
import OOSE_Final_Project.Blog.entity.blog.BlogInsight;
import OOSE_Final_Project.Blog.repository.*;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BlogInsightMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected TagRepository tagRepository;

    @Autowired
    protected BlogComicRepository blogComicRepository;

    @Autowired
    protected BlogCharacterRepository blogCharacterRepository;


    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategories")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    @Mapping(target = "comic", source = "comicId", qualifiedByName = "mapComic")
    @Mapping(target = "blogCharacter", source = "blogCharacterId", qualifiedByName = "mapBlogCharacter")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateBlogInsightFromDto(BlogInsightReq source, @MappingTarget BlogInsight target);

    @Mapping(target = "author", source = "author", qualifiedByName = "mapAuthorResponse")
    @Mapping(target = "comicId", source = "comic", qualifiedByName = "mapComicId")
    @Mapping(target = "blogCharacterId", source = "blogCharacter", qualifiedByName = "mapBlogCharacterId")
    public abstract void updateBlogInsightResponseFromEntity(
            BlogInsight source, @MappingTarget BlogInsightRes target);

    @Named("mapCategories")
    List<Category> mapCategories(List<Long> categoryIds) {
        if (categoryIds == null) {
            return null;
        }
        return categoryRepository.findAllById(categoryIds);
    }

    @Named("mapBlogCharacterId")
    Long mapBlogCharacterId(BlogCharacter blogCharacter) {
        return blogCharacter.getId();
    }

    @Named("mapComicId")
    Long mapComicId(BlogComic comic) {
        return comic.getId();
    }

    @Named("mapTags")
    List<Tag> mapTags(List<Long> tagIds) {
        if (tagIds == null) {
            return null;
        }
        return tagRepository.findAllById(tagIds);
    }

    @Named("mapAuthor")
    User mapAuthor(Long authorId) {
        return userRepository.findById(authorId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorId));
    }

    @Named("mapAuthorResponse")
    AuthorBlogRes mapAuthorResponse(User author) {
        return new AuthorBlogRes.Builder().avatar(author.getAvatar())
                                          .displayName(author.getDisplayName())
                                          .email(author.getEmail())
                                          .level(author.getLevel())
                                          .userId(author.getId())
                                          .build();
    }

    @Named("mapComic")
    BlogComic mapComic(Long comicId) {
        return blogComicRepository.findById(comicId)
                                  .orElseThrow(() -> new IllegalArgumentException(
                                          "BlogComic not found with id:" + comicId));
    }

    @Named("mapBlogCharacter")
    BlogCharacter mapBlogCharacter(Long blogCharacterId) {
        return blogCharacterRepository.findById(blogCharacterId)
                                      .orElseThrow(() -> new IllegalArgumentException(
                                              "BlogCharacter not found with id:" + blogCharacterId));
    }
}
