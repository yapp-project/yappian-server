package com.yapp.web1.service.impl;

import com.yapp.web1.common.MockTest;
import com.yapp.web1.domain.Account;
import com.yapp.web1.domain.VO.AccountRole;
import com.yapp.web1.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

public class AccountServiceImplTest extends MockTest {

    @Mock
    private AccountService accountService;

    private Account findAccount;

    @Before
    public void setup(){
        findAccount = Account.builder().name("테스트유저").email("test@test.com").role(AccountRole.USER).build();
    }

    @Test
    public void 유저임시메소드Test(){
        // given
        given(accountService.getCurrentUser()).willReturn(findAccount);

        // when
        final Account account = accountService.getCurrentUser();

        // then
        assertThat("테스트유저", is(account.getName()));
    }
}
