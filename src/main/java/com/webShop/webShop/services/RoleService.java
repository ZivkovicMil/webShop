package com.webShop.webShop.services;

import com.webShop.webShop.entities.Role;
import com.webShop.webShop.exceptions.EmailException;
import com.webShop.webShop.exceptions.RoleException;

import java.util.Set;

public interface RoleService {

    void addRole(Role role);

    Set<Role> addUserRoleToUser();

    void addRoleToUser(String user, String role) throws EmailException, RoleException;

    void removeRoleFromUser(String userEmail, String role) throws EmailException;
}
