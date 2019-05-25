package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.Url;
import com.yapp.web1.dto.req.UrlRequestDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UrlResponseDto;
import com.yapp.web1.exception.NotFoundException;
import com.yapp.web1.repository.UrlRepository;
import com.yapp.web1.service.CommonService;
import com.yapp.web1.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UrlServiceImpl implements UrlService {


    private final CommonService commonService;

    private final UrlRepository urlRepository;

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

    // urlResponseDto
    private List<UrlResponseDto> parseUrl(Long projectIdx) {
        List<Url> exUrlList = urlRepository.findByProjectIdx(projectIdx);
        List<UrlResponseDto> urlResponseDtos = new ArrayList<>();

        for (Url url : exUrlList) {
            UrlResponseDto dto = UrlResponseDto.builder()
                    .idx(url.getIdx())
                    .type(url.getType())
                    .title(url.getTitle())
                    .contents(url.getContents())
                    .build();
            urlResponseDtos.add(dto);
        }

        return urlResponseDtos;
    }

    // get Url
    @Transactional(readOnly = true)
    @Override
    public List<UrlResponseDto> getUrl(Long projectIdx) {

        Project findProject = commonService.findById(projectIdx);

        return parseUrl(projectIdx);
    }

    // create Url
    @Override
    public ProjectResponseDto createUrl(Long projectIdx, UrlRequestDto url, Long accountIdx) {

        Project findProject = commonService.findById(projectIdx);
        commonService.checkAccountPermission(commonService.getAccountListInProject(projectIdx), accountIdx);

        Url setUrl = Url.builder()
                .type(url.getType())
                .title(url.getTitle())
                .contents(url.getContents())
                .project(findProject)
                .build();

        urlRepository.save(setUrl);

        ProjectResponseDto projectResponseDto = ProjectResponseDto.builder()
                .project(findProject)
                .accountList(commonService.joinedProject(findProject))
                .urlList(parseUrl(projectIdx))
                .build();
        return projectResponseDto;
    }

    // deleteByUrlId
    @Override
    public void deleteUrl(Long projectIdx, final Long idx, Long accountIdx) {
        Project findProject = commonService.findById(projectIdx);

        commonService.checkAccountPermission(commonService.getAccountListInProject(projectIdx), accountIdx);
        checkNotFound(findProject.getUrlList(), idx);

        urlRepository.deleteById(idx);
    }

    // deleteAllUrl
    @Override
    public void deleteAllUrl(Long projectIdx) {
        urlRepository.deleteByProjectIdx(projectIdx);
    }

}
