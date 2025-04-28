package OOSE_Final_Project.Blog.service.impl;

import OOSE_Final_Project.Blog.dto.UserReq;
import OOSE_Final_Project.Blog.entity.User;
import OOSE_Final_Project.Blog.enums.ERole;
import OOSE_Final_Project.Blog.enums.EUserStatus;
import OOSE_Final_Project.Blog.mapper.UserMapper;
import OOSE_Final_Project.Blog.repository.UserRepository;
import OOSE_Final_Project.Blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;


    @Override
    public User createUser(UserReq userReq) {
        User user = new User();
        userMapper.updateUserFromDTO(userReq, user);
        // Kiểm tra username đã tồn tại
        if (userRepository.findByUsername(user.getUsername())
                          .isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Kiểm tra email đã tồn tại
        if (userRepository.findByEmail(user.getEmail())
                          .isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Thiết lập giá trị mặc định
        user.setRole(ERole.USER);
        user.setAccountStatus(user.getAccountStatus() != null ? user.getAccountStatus() : EUserStatus.ACTIVE);
        user.setVerified(false);
        user.setLevel(0);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUser(Long id, UserReq userDetails) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // Cập nhật username nếu không trùng
        if (userDetails.getUsername() != null && !userDetails.getUsername()
                                                             .equals(user.getUsername())) {
            if (userRepository.findByUsername(userDetails.getUsername())
                              .isPresent()) {
                throw new IllegalArgumentException("Username already exists");
            }
            user.setUsername(userDetails.getUsername());
        }

        // Cập nhật email nếu không trùng
        if (userDetails.getEmail() != null && !userDetails.getEmail()
                                                          .equals(user.getEmail())) {
            if (userRepository.findByEmail(userDetails.getEmail())
                              .isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(userDetails.getEmail());
        }

        // Cập nhật mật khẩu nếu có
        if (userDetails.getPassword() != null && !userDetails.getPassword()
                                                             .isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        userMapper.updateUserFromDTO(userDetails, user);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User verifyUser(Long id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        user.setVerified(true);
        return userRepository.save(user);
    }

    @Override
    public User updateUserStatus(Long id, EUserStatus status) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        user.setAccountStatus(status);
        return userRepository.save(user);
    }
}
