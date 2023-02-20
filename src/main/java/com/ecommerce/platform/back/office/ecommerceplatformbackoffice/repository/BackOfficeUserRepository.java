package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.repository;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity.BackOfficeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BackOfficeUserRepository extends JpaRepository<BackOfficeUser, Integer> {

    Optional<BackOfficeUser> findByUsername(String username);
}
