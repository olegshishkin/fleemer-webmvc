package com.fleemer.webmvc.repository;

import com.fleemer.webmvc.model.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByPersonId(Long id);
}
