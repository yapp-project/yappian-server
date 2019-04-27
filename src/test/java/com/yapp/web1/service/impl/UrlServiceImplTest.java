package com.yapp.web1.service.impl;

import com.yapp.web1.repository.UrlRepository;
import com.yapp.web1.service.ProjectService;
import com.yapp.web1.service.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlServiceImpl.class)
public class UrlServiceImplTest {

    @Mock
    private UrlRepository urlRepository;
    @Mock
    private ProjectService projectService;

    private UrlService urlService;

    //createUrl
    @Test
    public void createUrlTest(){

    }


}
