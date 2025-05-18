 package com.scm.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.user;
import java.util.List;



@Repository
public interface UserRepo extends JpaRepository<user,String>{

    //repostories are used to interact with database iske pass sab method hongr jo database se interaction krayenge
//extra method db relatedoperations
//custom query methods
Optional<user> findByEmail(String email);
Optional<user>findByEmailAndPassword(String email, String password);
}
