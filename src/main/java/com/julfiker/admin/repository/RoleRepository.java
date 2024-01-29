package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role findByRoleID(Long ID);

    List<Role> findAll();
    void deleteByRoleID(Long ID);
}