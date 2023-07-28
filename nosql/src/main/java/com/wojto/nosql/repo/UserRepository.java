package com.wojto.nosql.repo;

import com.wojto.nosql.model.UserAccount;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserAccount, String> {

    List<UserAccount> findByEmail(String email);

    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND sport.sportName = $1")
    List<UserAccount> findBySport(String sport);
    
    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND SEARCH(users, $1)")
    List<UserAccount> findByTextInUserAccount(String searchText);
}
