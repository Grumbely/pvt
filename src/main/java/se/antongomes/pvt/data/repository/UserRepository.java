package se.antongomes.pvt.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.antongomes.pvt.data.model.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByName(String name);
    public User findByNameAndPasswordHash(String name, String passwordHash);
}
