package com.doge.tip.model.repository.user;

import com.doge.tip.model.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.name = :name")
    public User getUserByName(@Param("name") String name);

}
