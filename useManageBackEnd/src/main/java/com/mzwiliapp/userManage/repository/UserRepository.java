package com.mzwiliapp.userManage.repository;

import com.mzwiliapp.userManage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    User findByEmail(String email);
}
