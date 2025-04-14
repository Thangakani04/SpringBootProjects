package com.springsecurity.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecurity.model.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer> {
    RoleModel findByRolename(String rolename);
}
