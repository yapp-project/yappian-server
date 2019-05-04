package com.yapp.web1.service.impl;

import com.yapp.web1.domain.File;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.User;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.*;
import com.yapp.web1.repository.FileRepository;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.service.CommonService;
import com.yapp.web1.service.FileService;
import com.yapp.web1.service.ProjectService;
import com.yapp.web1.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * ProjectService 구현 클래스
 *
 * @author Jihye Kim
 * @version 1.2
 * @since 0.0.4
 */

@Service
@Transactional
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final FileRepository fileRepository;
    private final UrlService urlService;
    private final FileService fileService;
    private final CommonService commonService;

    // projectDetail
    private ProjectResponseDto projectDetail(Project project, List<UserResponseDto> userList, List<UrlResponseDto> urlList) {

        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(project)
                .userList(userList)
                .urlList(urlList).build();
        return responseDto;
    }

    // userSet
    private Set<User> userSet(Long userIdx) {
        Set<User> userSet = new HashSet<>();
        userSet.add(commonService.findUserById(userIdx));

        return userSet;
    }

    // join 하기 - user
    private void joinedProject(Project project, Long userIdx) {
        User user = commonService.findUserById(userIdx);

        user.getJoinedProjects().add(project);
    }

    // createProject
    @Override
    public ProjectResponseDto createProject(ProjectRequestDto dto, Long userIdx) { //실제로는 User user. 그리고 User.getIdx()해서 구현

        final Project project = Project.builder()
                .type(dto.getProjectType())
                .name(dto.getProjectName())
                .finalCheck(Mark.N)
                .createUserIdx(userIdx)
                .password(dto.getPassword())
                .releaseCheck(Mark.N)
                .userList(userSet(userIdx))
                .orders(commonService.findOrdersById(dto.getOrdersIdx())).build();

        projectRepository.save(project); // create

        // user쪽 join
        joinedProject(project, userIdx);

        return projectDetail(project, commonService.joinedProject(project), new ArrayList<>());
    }

    //update project
    @Override
    public ProjectResponseDto updateProject(Long idx, ProjectRequestDto dto, Long userIdx) {
        Project project = commonService.findById(idx);

        // 수정 조건 : 프로젝트 조인된 사람만 수정할 수 있다.
        commonService.checkUserPermission(getUserListInProject(idx), userIdx);

        project.setOrders(commonService.findOrdersById(dto.getOrdersIdx()));
        project.setName(dto.getProjectName());
        project.setType(dto.getProjectType());
        project.setPassword(dto.getPassword());

        projectRepository.save(project); // update

        return projectDetail(project, commonService.joinedProject(project), urlService.getUrl(idx));
    }

    //delete project
    @Override
    public void deleteProject(Long idx, Long userIdx) //User user
    {
        Project findProject = commonService.findById(idx); // 해당 프로젝트 존재 여부 체크 위함

        // 수정 조건 : 프로젝트 조인된 사람만 수정할 수 있다.
        commonService.checkUserPermission(getUserListInProject(idx), userIdx);


        // parsUser 삭제
        List<UserResponseDto> userResponseDtos = commonService.joinedProject(findProject);
        User user = null;

        for (int i = 0; i < userResponseDtos.size(); ++i) {
            user = commonService.findUserById(userResponseDtos.get(i).getUserIdx());
            user.setJoinedProjects(null);
        }

        urlService.deleteAllUrl(idx); // 해당 프로젝트의 url 삭제
        fileService.deleteAllFile(idx); // 해당 프로젝트의 file 삭제
        projectRepository.deleteById(idx);
    }

    // get project
    @Transactional(readOnly = true)
    @Override
    public ProjectResponseDto getProject(Long idx) {
        Project project = commonService.findById(idx);

        return projectDetail(project, commonService.joinedProject(project), urlService.getUrl(idx));
    }

    // join project
    @Override
    public void joinProject(Long projectIdx, Long userIdx) {

        User user = commonService.findUserById(userIdx);
        Project project = commonService.findById(projectIdx);
        joinedProject(project, user.getIdx());

    }

    // userList
    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> getUserListInProject(Long idx) {

        Project project = commonService.findById(idx);

        return commonService.joinedProject(project);
    }

    // setFinishedProject
    @Override
    public FinishProjectResponseDto setFinishedProject(Long projectIdx, MultipartFile[] multipartFiles, FinishProjectRequestDto dto, Long userIdx) {
        System.out.println("projectImpl");
        Project project = commonService.findById(projectIdx);

        commonService.checkUserPermission(getUserListInProject(projectIdx), userIdx);

        // 파일 업로드
        List<FileUploadResponseDto> fileUploadResponseDtos = fileService.fileUpload(multipartFiles, projectIdx);

        System.out.println("fileUpload 성공");
        List<File> file = fileRepository.findByProjectIdx(projectIdx);

        // set finishedProject
        project.setDescription(dto.getDescription());
        project.setFinalCheck(Mark.Y);
        project.setFileList(file);
        projectRepository.save(project);

        System.out.println("set finishedProject");
        // res dto
        FinishProjectResponseDto finishProjectResponseDtos = null;

        finishProjectResponseDtos.builder()
                .projectIdx(project.getIdx())
                .ordersNumber(project.getOrders().getNumber())
                .projectName(project.getName())
                .projectType(project.getType())
                .productURL(project.getProductURL())
                .projectDescription(project.getDescription())
                .fileList(fileUploadResponseDtos)
                .build();

        return finishProjectResponseDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public FinishProjectResponseDto getFinishedProject(Long idx) {
        return null;
    }

}
