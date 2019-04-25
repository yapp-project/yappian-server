package com.yapp.web1.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.Url;
import com.yapp.web1.domain.VO.ProjectType;
import com.yapp.web1.domain.VO.UrlType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Mock
    private ProjectRepository projectRepository;

   // private Project project;
    private Url newUrl;

    @Before
    public void init() {
        /*
        this.project = Project.builder()
                .name("junit_test")
                .description("test")
                .urlList(null)
                .type(ProjectType.WEB)
                .build();
*/

        this.newUrl = Url.builder()
                .title("junit_test")
                .contents("test")
                .type(UrlType.FIRST)
                .project(projectRepository.findById(2016322L).get())
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
       // projectRepository.save(project);
        urlRepository.save(newUrl);

        Url findUrl = urlRepository.findById(newUrl.getIdx()).orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

        assertEqualsCompare(newUrl, findUrl);
    }

    @Test
    public void delete(){
      //  projectRepository.save(project);
        urlRepository.save(newUrl);

        Url findUrl = urlRepository.findById(newUrl.getIdx()).orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

        assertEqualsCompare(newUrl, findUrl);

        urlRepository.delete(newUrl);
        assertFalse(urlRepository.findById(newUrl.getIdx()).isPresent());
    }

    @Test
    public void findByProjectIdxTest(){
      //  projectRepository.save(project);
        urlRepository.save(newUrl);

        List<Url> findUrl = urlRepository.findByProjectIdx(20521L);

        assertEquals(newUrl.getTitle(), findUrl.get(0).getTitle());
        assertEquals(newUrl.getContents(), findUrl.get(0).getContents());
        assertEquals(newUrl.getType(), findUrl.get(0).getType());
        assertEquals(newUrl.getProject(), findUrl.get(0).getProject());
    }
}
