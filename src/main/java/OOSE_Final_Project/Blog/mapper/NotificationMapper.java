package OOSE_Final_Project.Blog.mapper;

import OOSE_Final_Project.Blog.dto.req.NotificationReq;
import OOSE_Final_Project.Blog.dto.res.NotificationRes;
import OOSE_Final_Project.Blog.dto.res.user.UserNotificationRes;
import OOSE_Final_Project.Blog.entity.Notification;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.repository.NotificationRepository;
import OOSE_Final_Project.Blog.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {UserRepository.class, NotificationRepository.class})
public abstract class NotificationMapper {

    @Autowired
    protected UserRepository userRepository;


    @Mapping(target = "sender", source = "senderId", qualifiedByName = "mapSender")
    @Mapping(target = "receiver", source = "receiverId", qualifiedByName = "mapReceiver")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateNotificationFromDto(
            NotificationReq notificationReq, @MappingTarget Notification notification);


    @Mapping(target = "sender", source = "sender", qualifiedByName = "mapSenderRes")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateNotificationResponseFromEntity(
            Notification source,
            @MappingTarget NotificationRes target);

    @Named("mapSenderRes")
    UserNotificationRes mapSenderRes(User sender) {
        return new UserNotificationRes.Builder().userId(sender.getId())
                                                .avatar(sender.getAvatar())
                                                .displayName(sender.getDisplayName())
                                                .loginType(sender.getLoginType())
                                                .build();
    }

    @Named("mapSender")
    User mapSender(Long senderId) {
        return userRepository.findById(senderId)
                             .orElseThrow(() -> new RuntimeException("Sender not found"));
    }

    @Named("mapReceiver")
    User mapReceiver(Long receiverId) {
        return userRepository.findById(receiverId)
                             .orElseThrow(() -> new RuntimeException("Receiver not found"));
    }
}
