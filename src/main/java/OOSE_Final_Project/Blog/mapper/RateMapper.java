package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.RateReq;
import OOSE_Final_Project.Blog.dto.res.RateRes;
import OOSE_Final_Project.Blog.entity.Rate;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.entity.blog.Blog;
import OOSE_Final_Project.Blog.repository.BlogRepository;
import OOSE_Final_Project.Blog.repository.RateRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {UserRepository.class, BlogRepository.class, RateRepository.class})
public abstract class RateMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected BlogRepository blogRepository;

    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUser")
    @Mapping(target = "blog", source = "blogId", qualifiedByName = "mapBlog")
    public abstract void updateRateFromDto(RateReq source, @MappingTarget Rate target);

    @Mapping(target = "userId", source = "user", qualifiedByName = "mapUserId")
    @Mapping(target = "blogId", source = "blog", qualifiedByName = "mapBlogId")
    public abstract void updateRateResponseFromEntity(Rate source, @MappingTarget RateRes target);

    @Named("mapUser")
    User mapUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                             .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }


    @Named("mapBlog")
    Blog mapBlog(Long blogId) {
        if (blogId == null) {
            return null;
        }
        return blogRepository.findById(blogId)
                             .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));
    }

    @Named("mapBlogId")
    long mapBlogId(Blog blog) {
        return blog.getId();
    }

    @Named("mapUserId")
    long mapUserId(User user) {
        return user.getId();
    }

}
