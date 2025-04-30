package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.res.NotificationRes;
import OOSE_Final_Project.Blog.dto.res.user.UserNotificationRes;
import OOSE_Final_Project.Blog.entity.Notification;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.repository.NotificationRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {UserRepository.class, NotificationRepository.class})
public abstract class NotificationMapper {

    @Autowired
    protected UserRepository userRepository;

    @Mapping(target = "receiver", source = "receiver", qualifiedByName = "mapReceiver")
    public abstract void updateNotificationResponseFromEntity(
            Notification source,
            @MappingTarget NotificationRes target);

    @Named("mapReceiver")
    UserNotificationRes mapReceiver(User receiver) {
        return new UserNotificationRes.Builder().userId(receiver.getId())
                                        .avatar(receiver.getAvatar())
                                        .displayName(receiver.getDisplayName())
                                        .build();
    }


}
