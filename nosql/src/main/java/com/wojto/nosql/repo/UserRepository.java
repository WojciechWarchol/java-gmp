package com.wojto.nosql.repo;

import com.wojto.nosql.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserAccount, String> {


}
