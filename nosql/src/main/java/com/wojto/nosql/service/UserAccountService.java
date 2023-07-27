package com.wojto.nosql.service;

import com.wojto.nosql.model.Sport;
import com.wojto.nosql.model.UserAccount;
import com.wojto.nosql.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    private final UserRepository userRepository;

    @Autowired
    public UserAccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserAccount getUserAccountById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Account for ID: " + id + "not found."));
    }

    public UserAccount createUserAccount(UserAccount userAccount) {
        return userRepository.save(userAccount);
    }

    public List<UserAccount> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserAccount addSportToUser(String id, String sportName) {
        return userRepository.findById(id)
                .map(userAccount -> {
                    userAccount.setSport(Sport.of(sportName));
                    return userRepository.save(userAccount);
                })
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));
    }
}
