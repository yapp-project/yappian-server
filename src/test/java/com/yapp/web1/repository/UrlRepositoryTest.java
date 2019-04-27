package com.yapp.web1.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.yapp.web1.common.RepositoryTest;
import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.Url;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.domain.VO.ProjectType;
import com.yapp.web1.domain.VO.UrlType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlRepositoryTest {

    // TODO 지혜 테스트 확인
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    private Project project;
    private Url newUrl;
    private Orders orders;

    @Before
    public void init() {
        this.orders = Orders.builder()
                .number(20)
                .build();

        this.project = Project.builder()
                .name("junit_test")
                .description("test")
                .urlList(null)
                .type(ProjectType.WEB)
                .createUserIdx(201632004L)
                .password("1234")
                .orders(orders)
                .finalCheck(Mark.N)
                .build();

        this.newUrl = Url.builder()
                .title("junit_test")
                .contents("test")
                .type(UrlType.FIRST)
                .project(project)
                .build();
    }

    private void assertEqualsCompare(Url url, Url other) {

        assertEquals(url.getTitle(), other.getTitle());
        assertEquals(url.getContents(), other.getContents());
        assertEquals(url.getType(), other.getType());
        assertEquals(url.getProject(), other.getProject());
    }

    @Test
    public void saveAndFind() {
//        ordersRepository.save(orders);
//        projectRepository.save(project);
//        urlRepository.save(newUrl);
//
//        Url findUrl = urlRepository.findById(newUrl.getIdx()).orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));
//
//        assertEqualsCompare(newUrl, findUrl);
    }

    @Test
    public void delete() {
//        ordersRepository.save(orders);
//        projectRepository.save(project);
//        urlRepository.save(newUrl);
//
//        Url findUrl = urlRepository.findById(newUrl.getIdx()).orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));
//
//        assertEqualsCompare(newUrl, findUrl);
//
//        urlRepository.delete(newUrl);
//        assertFalse(urlRepository.findById(newUrl.getIdx()).isPresent());
    }

    @Test
    public void findByProjectIdxTest() {
//        ordersRepository.save(orders);
//        projectRepository.save(project);
//        urlRepository.save(newUrl);
//
//        List<Url> findUrl = urlRepository.findByProjectIdx(project.getIdx());
//
//        assertEquals(newUrl.getTitle(), findUrl.get(0).getTitle());
//        assertEquals(newUrl.getContents(), findUrl.get(0).getContents());
//        assertEquals(newUrl.getType(), findUrl.get(0).getType());
//        assertEquals(newUrl.getProject(), findUrl.get(0).getProject());
    }
}
