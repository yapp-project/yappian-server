package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Account;
import com.yapp.web1.domain.File;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.*;
import com.yapp.web1.exception.NoPermissionException;
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
    private final UrlService urlService;
    private final FileService fileService;
    private final CommonService commonService;

    // finishResponseDto
    private FinishProjectResponseDto finishDto(Project project, List<FileUploadResponseDto> fileUploadResponseDtos) {

        FinishProjectResponseDto finishProjectResponseDto = FinishProjectResponseDto.builder()
                .projectIdx(project.getIdx())
                .ordersNumber(project.getOrders().getNumber())
                .projectName(project.getName())
                .projectType(project.getType())
                .productURL(project.getProductURL())
                .projectDescription(project.getDescription())
                .releaseCheck(project.getReleaseCheck())
                .fileList(fileUploadResponseDtos)
                .build();

        return finishProjectResponseDto;
    }

    // projectDetail
    private ProjectResponseDto projectDetail(Project project, List<AccountResponseDto> accountList, List<UrlResponseDto> urlList) {

        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(project)
                .accountList(accountList)
                .urlList(urlList).build();
        return responseDto;
    }

    // accountSet
    private Set<Account> accountSet(Long accountIdx) {
        Set<Account> accountSet = new HashSet<>();
        accountSet.add(commonService.findAccountById(accountIdx));

        return accountSet;
    }

    // join 하기 - account
    private void joinedProject(Project project, Long accountIdx) {
        Account account = commonService.findAccountById(accountIdx);

        account.getJoinedProjects().add(project);
    }

    // getProjectsList
    @Override
    public List<ProjectsResDto> getProjects() {

        List<Project> projects = projectRepository.findAll();

        List<ProjectsResDto> projectList = new ArrayList<>();

        for (Project project : projects) {

            List<AccountResponseDto> accountList = commonService.joinedProject(project);
            List<UrlResponseDto> urlList = urlService.getUrl(project.getIdx());
            List<FileUploadResponseDto> fileList = fileService.getFileList(project.getIdx());

            ProjectsResDto projectsDto = ProjectsResDto.builder()
                    .idx(project.getIdx())
                    .name(project.getName())
                    .password(project.getPassword())
                    .type(project.getType())
                    .ordersIdx(project.getOrders().getIdx())
                    .orderNumber(project.getOrders().getNumber())
                    .createUserIdx(project.getCreateAccountIdx())
                    .finalCheck(project.getFinalCheck())
                    .releaseCheck(project.getReleaseCheck())
                    .productURL(project.getProductURL())
                    .description(project.getDescription())
                    .accountList(accountList)
                    .urlList(urlList)
                    .fileList(fileList)
                    .build();
            projectList.add(projectsDto);
        }
        return projectList;
    }

    // createProject
    @Override
    public ProjectResponseDto createProject(ProjectRequestDto dto, Long accountIdx) {

        final Project project = Project.builder()
                .type(dto.getProjectType())
                .name(dto.getProjectName())
                .finalCheck(Mark.N)
                .createAccountIdx(accountIdx)
                .password(dto.getPassword())
                .releaseCheck(Mark.N)
                .accountList(accountSet(accountIdx))
                .orders(commonService.findOrdersById(dto.getOrdersIdx())).build();

        projectRepository.save(project); // create

        // account쪽 join
        joinedProject(project, accountIdx);

        return projectDetail(project, commonService.joinedProject(project), new ArrayList<>());
    }

    //update project
    @Override
    public ProjectResponseDto updateProject(Long idx, ProjectRequestDto dto, Long accountIdx) {
        Project project = commonService.findById(idx);

        // 수정 조건 : 프로젝트 조인된 사람만 수정할 수 있다.
        commonService.checkAccountPermission(getAccountListInProject(idx), accountIdx);

        project.setOrders(commonService.findOrdersById(dto.getOrdersIdx()));
        project.setName(dto.getProjectName());
        project.setType(dto.getProjectType());
        project.setPassword(dto.getPassword());

        projectRepository.save(project); // update

        return projectDetail(project, commonService.joinedProject(project), urlService.getUrl(idx));
    }

    //delete project
    @Override
    public void deleteProject(Long idx, Long accountIdx) //Account
    {
        Project findProject = commonService.findById(idx); // 해당 프로젝트 존재 여부 체크 위함

        // 수정 조건 : 프로젝트 조인된 사람만 수정할 수 있다.
        commonService.checkAccountPermission(getAccountListInProject(idx), accountIdx);

        // parsAccount 삭제
        List<AccountResponseDto> accountResponseDtos = commonService.joinedProject(findProject);
        Account account = null;

        for (int i = 0; i < accountResponseDtos.size(); ++i) {
            account = commonService.findAccountById(accountResponseDtos.get(i).getAccountIdx());
            account.setJoinedProjects(null);
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
    public void joinProject(Long projectIdx, String password, Long randomAccount) {

        Project project = commonService.findById(projectIdx);

        if (!project.getPassword().equals(password)) {
            throw new NoPermissionException("프로젝트 비밀번호 다릅니다.");
        }

        Account account = commonService.findAccountById(randomAccount);
        joinedProject(project, account.getIdx());

    }

    // accountList
    @Transactional(readOnly = true)
    @Override
    public List<AccountResponseDto> getAccountListInProject(Long idx) {

        Project project = commonService.findById(idx);

        return commonService.joinedProject(project);
    }

    // setFinishedProject
    @Override
    public FinishProjectResponseDto setFinishedProject(Long projectIdx, MultipartFile[] multipartFiles, FinishProjectRequestDto dto, Long accountIdx) {

        Project project = commonService.findById(projectIdx);

        commonService.checkAccountPermission(getAccountListInProject(projectIdx), accountIdx);

        // 파일 업로드
        List<FileUploadResponseDto> fileUploadResponseDtos = fileService.fileUpload(multipartFiles, projectIdx);

        List<File> file = fileService.getFiles(projectIdx);

        // set finishedProject
        project.setDescription(dto.getDescription());
        project.setProductURL(dto.getProductURL());
        project.setReleaseCheck(dto.getReleaseCheck());
        project.setFinalCheck(Mark.Y);
        project.setFileList(file);
        projectRepository.save(project);

        return finishDto(project, fileUploadResponseDtos);
    }

    // get finishedProject
    @Transactional(readOnly = true)
    @Override
    public FinishProjectResponseDto getFinishedProject(Long projectIdx) {
        Project project = commonService.findById(projectIdx);
        List<FileUploadResponseDto> fileUploadResponseDtos = fileService.getFileList(projectIdx);

        return finishDto(project, fileUploadResponseDtos);
    }

    // leave project
    @Override
    public void leaveProject(Long projectIdx, Long accountIdx) {
        Account account = commonService.findAccountById(accountIdx);

        Set<Project> projectSet = account.getJoinedProjects();
        Set<Project> result = new HashSet<>();
        for(Project pro : projectSet){
            result.add(pro);
        }
        Iterator<Project> it = projectSet.iterator();
        Project p;
        while (it.hasNext()) {
            p = it.next();
            if(p.getIdx()==projectIdx){
                result.remove(p);
                break;
            }
        }
        account.getJoinedProjects().clear();
        account.setJoinedProjects(result);
    }
}
