package OOSE_Final_Project.Blog.service;


import OOSE_Final_Project.Blog.dto.UserReq;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.enums.EUserStatus;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(UserReq user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    User updateUser(Long id, UserReq userDetails);

    void deleteUser(Long id);

    User verifyUser(Long id);

    User updateUserStatus(Long id, EUserStatus status);
}
