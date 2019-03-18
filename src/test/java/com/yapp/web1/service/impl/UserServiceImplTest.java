package com.yapp.web1.service.impl;

import com.yapp.web1.common.MockTest;
import com.yapp.web1.domain.User;
import com.yapp.web1.domain.VO.UserRole;
import com.yapp.web1.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

public class UserServiceImplTest extends MockTest {

    @Mock
    private UserService userService;

    private User findUser;

    @Before
    public void setup(){
        findUser = User.builder().name("테스트유저").email("test@test.com").role(UserRole.USER).build();
    }

    @Test
    public void 유저임시메소드Test(){
        // given
        given(userService.getCurrentUser()).willReturn(findUser);

        // when
        final User user = userService.getCurrentUser();

        // then
        assertThat("테스트유저", is(user.getName()));
    }
}
