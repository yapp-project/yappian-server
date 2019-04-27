package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.Url;
import com.yapp.web1.dto.req.UrlRequestDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UrlResponseDto;
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.exception.Url.NoPermissionException;
import com.yapp.web1.exception.Url.NotFoundException;
import com.yapp.web1.exception.Url.UrlException;
import com.yapp.web1.repository.UrlRepository;
import com.yapp.web1.service.ProjectService;
import com.yapp.web1.service.UrlService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final ProjectService projectService;

    private final UrlRepository urlRepository;

    private final ModelMapper modelMapper;

    // not found 검사
    private void checkNotFound(List<Url> urlList, Long urlIdx) {
        boolean check = false;
        for (Url url : urlList) {
            if ((url.getIdx()).equals((urlIdx))) {
                check = true;
            }
        }
        if (!check) {
            throw new NotFoundException("해당 url 없습니다.");
        }
    }

    // user 권한 검사
    private void checkUserPermission(List<UserResponseDto> userList, Long userIdx) {
        boolean check = false;
        for (UserResponseDto user : userList) {
            if ((user.getUserIdx()).equals((userIdx))) {
                check = true;
            }
        }
        if (!check) {
            throw new NoPermissionException("이 유저는 권한이 없습니다.");
        }
    }

    @Override
    public List<UrlResponseDto> getUrl(Long projectIdx){

        Project findProject = projectService.findById(projectIdx);

        List<Url> exUrlList = urlRepository.findByProjectIdx(projectIdx);
        List<UrlResponseDto> urlResponseDtos = new ArrayList<>();
        for (int i = 0; i < exUrlList.size(); ++i) {
            urlResponseDtos.add(new UrlResponseDto(exUrlList.get(i).getIdx(), exUrlList.get(i).getType(), exUrlList.get(i).getTitle(), exUrlList.get(i).getContents()));
        }


        return  urlResponseDtos;
    }
    @Override
    public ProjectResponseDto createUrl(Long projectIdx, UrlRequestDto urlRequestDto, Long userIdx) throws UrlException {

        Project findProject = projectService.findById(projectIdx);

        checkUserPermission(projectService.getUserListInProject(projectIdx), userIdx);

//        Url setUrl = Url.builder()
//                .type(urlRequestDto.getType())
//                .title(urlRequestDto.getTitle())
//                .contents(urlRequestDto.getContents())
//                .project(findProject)
//                .build();
        // TODO modelMapper로 변경 확인 후 삭제
        Url setUrl = modelMapper.map(urlRequestDto, Url.class);

        urlRepository.save(setUrl);

        List<Url> exUrlList = urlRepository.findByProjectIdx(projectIdx);
        List<UrlResponseDto> urlResponseDtos = new ArrayList<>();
        for (int i = 0; i < exUrlList.size(); ++i) {
            urlResponseDtos.add(new UrlResponseDto(exUrlList.get(i).getIdx(), exUrlList.get(i).getType(), exUrlList.get(i).getTitle(), exUrlList.get(i).getContents()));
        }

        ProjectResponseDto projectResponseDto = ProjectResponseDto.builder()
                .project(findProject)
                .urlList(urlResponseDtos)
                .build();
        return projectResponseDto;
    }

    @Override
    public void deleteUrl(Long projectIdx, final Long idx, Long userIdx) {
        Project findProject = projectService.findById(projectIdx);

        checkUserPermission(projectService.getUserListInProject(projectIdx), userIdx);
        checkNotFound(findProject.getUrlList(), idx);

        urlRepository.deleteById(idx);
    }
}
