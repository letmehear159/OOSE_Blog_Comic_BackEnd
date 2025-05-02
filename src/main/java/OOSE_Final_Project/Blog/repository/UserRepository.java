package OOSE_Final_Project.Blog.repository;

import OOSE_Final_Project.Blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :id OR u.email = :id")
    Optional<User> findByUsernameOrEmail(@Param("id") String id);

    Optional<User> findByEmail(String email);

}
