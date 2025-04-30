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
    protected CategoryRepository categoryRepository;

    @Autowired
    protected TagRepository tagRepository;

    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "comic", source = "comicId", qualifiedByName = "mapComic")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateBlogCharacterFromDto(BlogCharacterReq source, @MappingTarget BlogCharacter target);

    @Mapping(target = "author", source = "author", qualifiedByName = "mapAuthorResponse")
    @Mapping(target = "character",ignore = true)
    public abstract void updateBlogCharacterResponseFromEntityWithoutDetail(
            BlogCharacter source, @MappingTarget BlogCharacterRes target);

    @Mapping(target = "author", source = "author", qualifiedByName = "mapAuthorResponse")
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

}