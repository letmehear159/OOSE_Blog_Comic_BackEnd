package OOSE_Final_Project.Blog.controller;

import OOSE_Final_Project.Blog.dto.req.UserReq;
import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import OOSE_Final_Project.Blog.dto.res.user.UserRes;
import OOSE_Final_Project.Blog.enums.EUserStatus;
import OOSE_Final_Project.Blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping
    public ApiResponse<UserRes> createUser(@RequestBody UserReq user) {
        UserRes created = userService.createUser(user);
        return new ApiResponse<>(HttpStatus.CREATED, "User created successfully", created, null);
    }

    // Lấy tất cả user
    @GetMapping
    public ApiResponse<List<UserRes>> getAllUsers() {
        List<UserRes> users = userService.getAllUsers();
        return new ApiResponse<>(HttpStatus.OK, "Fetched all users", users, null);
    }

    // Lấy user theo ID
    @GetMapping("/{id}")
    public ApiResponse<UserRes> getUserById(@PathVariable Long id) {
        UserRes user = userService.getUserById(id);
        return new ApiResponse<>(HttpStatus.OK, "Fetched user by ID", user, null);
    }

    // Lấy user theo username
    @GetMapping("/username/{username}")
    public ApiResponse<UserRes> getUserByUsername(@PathVariable String username) {
        UserRes user = userService.getUserByUsername(username);
        return new ApiResponse<>(HttpStatus.OK, "Fetched user by username", user, null);
    }

    // Lấy user theo email
    @GetMapping("/email/{email}")
    public ApiResponse<UserRes> getUserByEmail(@PathVariable String email) {
        UserRes user = userService.getUserByEmail(email);
        return new ApiResponse<>(HttpStatus.OK, "Fetched user by email", user, null);
    }

    // Cập nhật thông tin user
    @PutMapping("/{id}")
    public ApiResponse<UserRes> updateUser(@PathVariable Long id, @RequestBody UserReq userDetails) {
        UserRes updated = userService.updateUser(id, userDetails);
        return new ApiResponse<>(HttpStatus.OK, "User updated successfully", updated, null);
    }

    // Cập nhật trạng thái user
    @PutMapping("/{id}/status")
    public ApiResponse<UserRes> updateUserStatus(@PathVariable Long id, @RequestParam EUserStatus status) {
        UserRes updated = userService.updateUserStatus(id, status);
        return new ApiResponse<>(HttpStatus.OK, "User status updated successfully", updated, null);
    }

    // Xoá user
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT, "User deleted successfully", null, null);
    }

}
