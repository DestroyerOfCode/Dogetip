package com.doge.tip.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {

    @Query(value = "SELECT a from Authority a WHERE a.authorityName = :authorityName")
    Optional<Authority> getAuthorityByAuthorityName(@Param(value = "authorityName") String authorityName);
}
