package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.Roles;
import com.bilgeadam.repository.enums.Status;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long > {



    Optional<Auth> findOptionalByUsername(String username);
    Optional<Auth> findOptionalByUsernameAndPassword(String username,String password);

    @Query("select  a from Auth as  a  where  a.status='ACTIVE'" )
    List<Auth>  getAllActivateStatus();
    List<Auth> findAllByStatus(Status status);

    List<Auth> findAllOptionalByRole(Roles role);
}
