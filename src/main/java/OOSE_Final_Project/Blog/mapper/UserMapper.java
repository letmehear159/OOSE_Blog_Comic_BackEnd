package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.UserReq;
import OOSE_Final_Project.Blog.dto.res.user.UserRes;
import OOSE_Final_Project.Blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Mapping(target = "password", source = "password", qualifiedByName = "mapPassword")
    @Mapping(target = "avatar",ignore = true)
    public abstract void transformToEntityFromRequest(UserReq source, @MappingTarget User target);

    @Mapping(target = "email",ignore = true)
    public abstract void updateEntityFromRequest(UserReq source, @MappingTarget User target);

    public abstract void updateUserResponseFromEntity(User source, @MappingTarget UserRes target);

    @Named("mapPassword")
    String mapPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
