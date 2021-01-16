package com.webShop.webShop.controllers;

import com.webShop.webShop.entities.Role;
import com.webShop.webShop.exceptions.EmailException;
import com.webShop.webShop.exceptions.RoleException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.models.UserDTO;
import com.webShop.webShop.repository.UserRepository;
import com.webShop.webShop.services.RoleService;
import com.webShop.webShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ResponseEntity<Page<UserDTO>> allUsers(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        Page<UserDTO> userPage = userService.findAll(pageable);
        return new ResponseEntity(userPage, HttpStatus.OK);
    }

    @RequestMapping(value = "/addRole/{role}", method = RequestMethod.POST)
    public ResponseEntity addNewRole(@PathVariable("role") String role) {
        roleService.addRole(new Role(role));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "role/addAdmin/", method = RequestMethod.POST)
    public ResponseEntity addAdminRoleToUser(@RequestParam("email") String userEmail, @RequestParam("role") String role) throws EmailException, RoleException {
        roleService.addRoleToUser(userEmail, role);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "role/removeRole/", method = RequestMethod.POST)
    public ResponseEntity removeRoleFromUser(@RequestParam("email") String userEmail, @RequestParam("role") String role) throws EmailException {
        roleService.removeRoleFromUser(userEmail, role);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "turnOffUser/{email}", method = RequestMethod.POST)
    public ResponseEntity turnOffUser(@PathVariable("email") String email) throws UserNotFoundException {
        userService.turnOffUser(email);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "turnOnUser/{email}", method = RequestMethod.POST)
    public ResponseEntity turnOnUser(@PathVariable("email") String email) throws UserNotFoundException {
        userService.turnOnUser(email);
        return new ResponseEntity(HttpStatus.OK);
    }
}
