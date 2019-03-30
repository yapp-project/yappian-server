package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.Task;
import com.yapp.web1.domain.User;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.TaskListResponseDto;
import com.yapp.web1.repository.OrdersRepository;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.repository.TaskRepository;
import com.yapp.web1.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectService 구현 클래스
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @since 0.0.3
 * @version 1.2
 */
@Service
@Transactional
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final OrdersRepository ordersRepository;
    private final TaskRepository taskRepository;

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto dto, Long userIdx) {
        Orders order = ordersRepository.findById(dto.getOrdersIdx()).orElseThrow(()-> new EntityNotFoundException("기수 못찾음"));
        final Project project = Project.builder()
                .type(dto.getProjectType())
                .name(dto.getProjectName())
                .finalCheck(Mark.N)
                .createUserIdx(userIdx)
                .orders(order).build();

        projectRepository.save(project); // create

        // 생성후 바로 프로젝트 상세 정보를 보여주기 위함.
        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .orderNumber(order.getNumber())
                .projectType(project.getType())
                .projectName(project.getName())
                .taskList(new ArrayList<>()).build(); // 생성할 때는 빈 Task 목록
        return responseDto;
    }

    @Override
    public ProjectResponseDto updateProject(Long idx, ProjectRequestDto dto, Long userIdx)
    {
        // projectIdx로 createUserIdx를 찾는다. 그리고 비교
        Project project = projectRepository.findById(idx).orElseThrow(()->new EntityNotFoundException("해당 프로젝트 없음"));
        Long createUserIdx = project.getCreateUserIdx();
        Orders order = ordersRepository.findById(dto.getOrdersIdx()).orElseThrow(()-> new EntityNotFoundException("기수 못찾음"));
        List<Task> taskList = taskRepository.findByProjectIdx(idx);
        List<TaskListResponseDto> taskListResponseDtos = new ArrayList<>();
        for(Task tasks : taskList)
            taskListResponseDtos.add(new TaskListResponseDto(tasks));

        if(String.valueOf(createUserIdx).equals(String.valueOf(userIdx))){
            //기수, 타입, 이름
            project.builder()
                    .type(dto.getProjectType())
                    .name(dto.getProjectName())
                    .orders(order).build();
            projectRepository.save(project); // update
        }else{
            System.out.println("생성한 유저만 수정할 수 있음");
            return null; // 예외처리하기
        }

        /*
        List<Task> getTaskList = project.getTaskList();
        List<TaskListResponseDto> taskList = new ArrayList<>();
        for(Task task : getTaskList){
            TaskListResponseDto.builder().taskIdx(task.getIdx())
                    .taskTitle(task.getTitle())
                    .taskJob(task.getJob())

            taskList.add()
        }*/

        // 수정 후 바로 프로젝트 상세 정보를 보여주기 위함.
        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .orderNumber(order.getNumber())
                .projectType(project.getType())
                .projectName(project.getName())
                .taskList(null).build(); // 해당 프로젝트의 Task 목록 불러와야함
        return responseDto;
    }

    @Override
    public boolean deleteProject(Long idx, User user) {
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectResponseDto getProject(Long idx) {
        Project project = projectRepository.findById(idx).orElseThrow(()->new EntityNotFoundException("해당 프로젝트 없음"));
        List<Task> taskList = taskRepository.findByProjectIdx(idx);
        List<TaskListResponseDto> taskListResponseDtos = new ArrayList<>();
        for(Task tasks : taskList)
            taskListResponseDtos.add(new TaskListResponseDto(tasks));

        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(project)
                .taskList(taskListResponseDtos).build();
        return responseDto;
    }

    @Override
    public boolean setFinishedProject(Long idx, FinishProjectRequestDto dto) {
        // 예시
        Project findProject = findById(idx);

        if(findProject.getFinalCheck() == Mark.Y) return false; // 추후 수정

        findProject.updateProductURL(dto.getProductURL());
        findProject.describeProject(dto.getDescription());
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
