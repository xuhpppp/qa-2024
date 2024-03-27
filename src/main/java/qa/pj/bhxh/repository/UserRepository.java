package qa.pj.bhxh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qa.pj.bhxh.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
