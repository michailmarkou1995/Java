package com.userfrontend.dao;

import com.userfrontend.security.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleDao extends CrudRepository<Role, Integer> {//CrudRepository JpaRepository

    Role findByName(String name);
}
