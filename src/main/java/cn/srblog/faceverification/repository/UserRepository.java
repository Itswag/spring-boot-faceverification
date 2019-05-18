package cn.srblog.faceverification.repository;

import cn.srblog.faceverification.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsernameAndPassword(String username, String password);

    User findByToken(String token);
}
