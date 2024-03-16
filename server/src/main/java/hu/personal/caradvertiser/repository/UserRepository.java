package hu.personal.caradvertiser.repository;

import hu.personal.caradvertiser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
