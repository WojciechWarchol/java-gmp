package com.wojto.nosql.controller;

import com.wojto.nosql.model.UserAccount;
import com.wojto.nosql.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public UserAccount getUserAccountById(@PathVariable("id") String id) {
        return userAccountService.getUserAccountById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAccount createUserAccount(@RequestBody UserAccount userAccount) {
        return userAccountService.createUserAccount(userAccount);
    }

    @GetMapping("/users/email/{email}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<UserAccount> findUsers(@PathVariable("email") String email) {
        return userAccountService.findByEmail(email);
    }
}
