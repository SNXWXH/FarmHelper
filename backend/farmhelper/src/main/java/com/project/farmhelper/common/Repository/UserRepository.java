package com.project.farmhelper.common.Repository;

import com.project.farmhelper.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
