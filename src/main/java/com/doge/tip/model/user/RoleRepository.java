package com.doge.tip.model.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {

    @Query(value = "SELECT r from Role r WHERE r.roleName = :roleName")
    Optional<Role> getRoleByRoleName(@Param(value = "roleName") String roleName);
}
