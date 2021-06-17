package com.userfrontend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.userfrontend.security.Role;


public interface RoleDao extends CrudRepository <Role, Integer> {//CrudRepository JpaRepository
    Role findByName(String name);
}
