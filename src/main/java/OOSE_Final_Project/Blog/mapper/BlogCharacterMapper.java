package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.BlogCharacterReq;
import OOSE_Final_Project.Blog.entity.Category;
import OOSE_Final_Project.Blog.entity.Tag;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.BlogCharacter;
import OOSE_Final_Project.Blog.entity.blog.BlogComic;
import OOSE_Final_Project.Blog.repository.BlogComicRepository;
import OOSE_Final_Project.Blog.repository.CategoryRepository;
import OOSE_Final_Project.Blog.repository.TagRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategories")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    public abstract void updateBlogCharacterFromDto(BlogCharacterReq source, @MappingTarget BlogCharacter target);

    @Named("mapAuthor")
    User mapAuthor(Long authorId) {
        return userRepository.findById(authorId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authorId));
    }

    @Named("mapComic")
    BlogComic mapComic(Long comicId) {
        if(comicId == null){
            return null;
        }

        return blogComicRepository.findById(comicId)
                                  .orElseThrow(() -> new IllegalArgumentException(
                                          "BlogComic not found with id:" + comicId));
    }

    @Named("mapCategories")
    List<Category> mapCategories(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    @Named("mapTags")
    List<Tag> mapTags(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }
}