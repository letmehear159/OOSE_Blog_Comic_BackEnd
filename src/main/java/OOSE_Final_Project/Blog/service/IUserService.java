package OOSE_Final_Project.Blog.service;


import OOSE_Final_Project.Blog.dto.req.UserReq;
import OOSE_Final_Project.Blog.dto.res.user.UserRes;
import OOSE_Final_Project.Blog.enums.EUserStatus;

import java.util.List;

public interface IUserService {
    UserRes createUser(UserReq user);

    List<UserRes> getAllUsers();

    UserRes getUserById(Long id);

    UserRes getUserByUsername(String username);

    UserRes getUserByEmail(String email);

    UserRes updateUser(Long id, UserReq userDetails);

    void deleteUser(Long id);

    UserRes updateUserStatus(Long id, EUserStatus status);
}
