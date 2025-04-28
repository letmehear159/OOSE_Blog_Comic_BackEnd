package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.UserReq;
import OOSE_Final_Project.Blog.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UserReq userDTO, @MappingTarget User user);

}
