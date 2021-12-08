package pl.turistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.turistica.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public int countAllByEmail(String email);
    public User findByEmail(String email);
}
