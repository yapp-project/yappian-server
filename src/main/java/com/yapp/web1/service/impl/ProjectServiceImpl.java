package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.User;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.ProjectSaveResponseDto;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * ProjectService 구현 클래스
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.1
 */
@Service
@Transactional
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public ProjectSaveResponseDto saveProject(){
        return null;}

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto dto, Long userIdx) {
        Orders orders;
        orders = Orders.builder().number(dto.getOrderNumber()).build();

        final Project project = Project.builder()
                .type(dto.getProjectType())
                .name(dto.getProjectName())
                .orders(orders)
                .createUserIdx(userIdx).build();

        projectRepository.save(project); // create

        // 생성후 바로 프로젝트 상세 정보를 보여주기 위함.
        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(dto)
                .taskList(null).build();
        return responseDto;
    }

    @Override
    public boolean deleteProject(Long idx, User user) {
        return false;
    }

    @Override
    public ProjectResponseDto updateProject(Long idx, ProjectRequestDto dto, User user) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectResponseDto getProject(Long idx) {
        return null;
    }

    @Override
    public boolean setFinishedProject(Long idx, FinishProjectRequestDto dto) {
        // 예시
        Project findProject = findById(idx);

        if(findProject.getFinalCheck() == Mark.Y) return false; // 추후 수정

        findProject.updateProductURL(dto.getProductURL());
        findProject.descriptProject(dto.getDescription());
        findProject.finishedProject();
        projectRepository.save(findProject);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public FinishProjectResponseDto getFinishedProject(Long idx) {
        return null;
    }

    private Project findById(Long idx){
        return projectRepository.findById(idx).orElseThrow(
                ()-> new IllegalArgumentException("findById error : wrong id"));
    }
}
