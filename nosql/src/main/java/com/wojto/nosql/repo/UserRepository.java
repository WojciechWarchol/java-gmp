package com.wojto.nosql.repo;

import com.wojto.nosql.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserAccount, String> {

    List<UserAccount> findByEmail(String email);

}
