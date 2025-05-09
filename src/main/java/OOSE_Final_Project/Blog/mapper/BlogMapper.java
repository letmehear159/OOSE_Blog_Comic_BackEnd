package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.res.blog.BlogRes;
import OOSE_Final_Project.Blog.dto.res.user.AuthorBlogRes;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class BlogMapper {

    @Mapping(target = "author", source = "author", qualifiedByName = "mapAuthorResponse")
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
}
