package com.yapp.web1.repository;

import com.yapp.web1.domain.Account;
import com.yapp.web1.social.AccountConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByName(String name);
    Account findBySocial(AccountConnection accountConnection);
}
