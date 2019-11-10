package com.yapp.web1.controller;

import com.google.gson.Gson;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.UrlType;
import com.yapp.web1.dto.req.UrlRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author KoDakyung
 * @since 2019-11-10
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UrlController.class)
public class UrlControllerTest {
    @Autowired
    MockMvc mvc;

    // TODO mock 의존성 코드 추가하고 재작성
    @Mock
    private Project project;

    @Test
    public void createUrl() throws Exception {
        UrlRequestDto url = UrlRequestDto.builder()
                .title("3차 데브 캠프")
                .contents("test.com")
                .type(UrlType.THIRD)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(url);

        mvc.perform(post("/api/project/{projectIdx}/url", project.getIdx())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
}