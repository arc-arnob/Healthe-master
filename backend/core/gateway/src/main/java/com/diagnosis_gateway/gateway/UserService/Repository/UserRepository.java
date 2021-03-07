package  com.diagnosis_gateway.gateway.UserService.Repository;

import java.util.Optional;

import  com.diagnosis_gateway.gateway.UserService.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    
}
