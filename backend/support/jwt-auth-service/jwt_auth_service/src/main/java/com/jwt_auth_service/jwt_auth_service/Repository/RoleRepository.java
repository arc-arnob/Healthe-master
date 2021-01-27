package com.jwt_auth_service.jwt_auth_service.Repository;

import com.jwt_auth_service.jwt_auth_service.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
