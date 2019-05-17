package com.yapp.web1.repository;

import com.yapp.web1.domain.User;
import com.yapp.web1.social.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String name);
    User findBySocial(UserConnection userConnection);
}
