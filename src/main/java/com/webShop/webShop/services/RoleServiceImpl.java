package com.webShop.webShop.services;

import com.webShop.webShop.Messages;
import com.webShop.webShop.entities.Role;
import com.webShop.webShop.entities.User;
import com.webShop.webShop.exceptions.EmailException;
import com.webShop.webShop.exceptions.RoleException;
import com.webShop.webShop.repository.RoleRepository;
import com.webShop.webShop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
        log.info(role.getRole_name() + " is added into database");
    }

    @Override
    public Set<Role> addUserRoleToUser() {
        Set<Role> set = new HashSet<>();
        set.add(roleRepository.findRole("USER"));
        log.info("Role USER is added to user");
        return set;
    }

    @Override
    public void addRoleToUser(String userEmail, String role) throws EmailException, RoleException {
        User user = userRepository.findByEmail(userEmail);
        Role addingRole = roleRepository.findRole(role);
        try {
            if (user == null) throw new EmailException(Messages.EMAIL_DOESNT_EXIST_IN_DATABSE);
            if (addingRole == null) throw new RoleException(Messages.ROLE_DOESNT_EXIST);
        } catch (EmailException e) {
            throw new EmailException(Messages.EMAIL_DOESNT_EXIST_IN_DATABSE);
        } catch (RoleException r) {
            throw new RoleException(Messages.EMAIL_DOESNT_EXIST_IN_DATABSE);
        }
        user.getList_of_roles().add(addingRole);
        userRepository.save(user);
        log.info(role + " is added to " + user.getEmail());
    }

    @Override
    public void removeRoleFromUser(String userEmail, String role) throws EmailException {
        User user = userRepository.findByEmail(userEmail);
        try {
            if (user == null) throw new EmailException(Messages.EMAIL_DOESNT_EXIST_IN_DATABSE);
            Set<Role> roles = user.getList_of_roles();
            for (Role r : roles) {
                if (r.getRole_name().equals(role)) {
                    roles.remove(r);
                    userRepository.save(user);
                    log.info(role + " is removed from user with email: " + user.getEmail());
                    break;
                }
            }
        } catch (EmailException e) {
            throw new EmailException(Messages.EMAIL_DOESNT_EXIST_IN_DATABSE);
        }
    }
}