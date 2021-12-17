package pl.turistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.turistica.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsUserByEmail(String email);

    User findByEmail(String email);

    User findUserById(int id);
}
