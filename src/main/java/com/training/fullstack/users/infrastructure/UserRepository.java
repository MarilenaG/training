package com.training.fullstack.users.infrastructure;

import com.training.fullstack.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserName(String userName);
}
