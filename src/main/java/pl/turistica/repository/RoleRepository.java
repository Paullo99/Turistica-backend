package pl.turistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.turistica.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Role findByName(String role);
}
