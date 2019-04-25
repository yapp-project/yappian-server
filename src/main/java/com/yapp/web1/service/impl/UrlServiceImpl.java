package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.Url;
import com.yapp.web1.dto.req.UrlRequestDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.exception.Url.NoPermissionException;
import com.yapp.web1.exception.Url.NotFoundException;
import com.yapp.web1.exception.Url.UrlException;
import com.yapp.web1.repository.UrlRepository;
import com.yapp.web1.service.ProjectService;
import com.yapp.web1.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final ProjectService projectService;
    private final UrlRepository urlRepository;

    // not found 검사
    private void checkNotFound(List<Url> urlList, Long urlIdx){
        boolean check = false;
        for(Url url : urlList ){
            if(String.valueOf(url.getIdx()).equals(String.valueOf(urlIdx))){
                check = true;
            }
        }
        if(!check){
            throw new NotFoundException("해당 url 없습니다.");
        }
    }
    // user 권한 검사
    private void checkUserPermission(List<UserResponseDto> userList, Long userIdx) {
        boolean check = false;
        for (UserResponseDto user : userList) {
            if (String.valueOf(user.getUserIdx()).equals(String.valueOf(userIdx))) {
                check = true;
            }
        }
        if (!check) {
            throw new NoPermissionException("이 유저는 권한이 없습니다.");
        }
    }

    @Override
    public ProjectResponseDto createUrl(UrlRequestDto url, Long userIdx) throws UrlException {

        Project findProject = projectService.findById(url.getProjectIdx());

        checkUserPermission(projectService.getUserListInProject(url.getProjectIdx()), userIdx);

        Url setUrl = Url.builder()
                .project(findProject)
                .type(url.getType())
                .title(url.getTitle())
                .contents(url.getContents())
                .build();

        urlRepository.save(setUrl);

        List<Url> exUrlList = urlRepository.findByProjectIdx(url.getProjectIdx());
        exUrlList.add(setUrl);

        ProjectResponseDto projectResponseDto = ProjectResponseDto.builder()
                .project(findProject)
                .urlList(exUrlList)
                .build();

        return projectResponseDto;
    }

    @Modifying
    @Override
    public void deleteUrl(final Long idx, Long userIdx) {
        Project findProject = projectService.findById(idx);

        checkUserPermission(projectService.getUserListInProject(idx), userIdx);
        checkNotFound(findProject.getUrlList(),idx);

        urlRepository.deleteById(idx);
    }
}
