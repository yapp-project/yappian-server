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
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.repository.OrdersRepository;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.repository.TaskRepository;
import com.yapp.web1.repository.UserRepository;
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
 * @version 1.2
 * @since 0.0.3
 */
@Service
@Transactional
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final OrdersRepository ordersRepository;
    private final TaskRepository taskRepository;

    // project findById
    private Project findById(Long idx){
        return projectRepository.findById(idx).orElseThrow(() -> new EntityNotFoundException("해당 프로젝트 없음"));
    }

    // createProject
    @Override
    public ProjectResponseDto createProject(ProjectRequestDto dto, Long userIdx) { //실제로는 User user. 그리고 User.getIdx()해서 구현
        Orders order = ordersRepository.findById(dto.getOrdersIdx()).orElseThrow(() -> new EntityNotFoundException("기수 못찾음"));
        final Project project = Project.builder()
                .type(dto.getProjectType())
                .name(dto.getProjectName())
                .finalCheck(Mark.N)
                .createUserIdx(userIdx)
                .orders(order).build();

        projectRepository.save(project); // create

        // 생성후 바로 프로젝트 상세 정보를 보여주기 위함.
        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(project)
                .taskList(new ArrayList<>()).build(); // 생성할 때는 빈 Task 목록
        return responseDto;
    }

    // taskList와 각각의 userList GET
    private List<TaskListResponseDto> getTaskUser(Long projectIdx){

        // 해당 프로젝트의 task 목록
        List<Task> taskList = taskRepository.findByProjectIdx(projectIdx);

        // 해당 프로젝트의 task목록에서 각 task의 담당 user목록들을 저장
        List<List<User>> users = new ArrayList<List<User>>();

        for (int i = 0; i < taskList.size(); ++i) {
            users.get(i).addAll(taskList.get(i).getWorks());
        }

        // user의 idx와 name만 출력하는 UserResponseDto list에 위에서 저장한 각 task의 userList에서 원하는 값들만 저장한다.
        List<UserResponseDto> userList = new ArrayList<>();
        for (int i = 0; i < users.size(); ++i) {
            userList.add(new UserResponseDto(users.get(i).get(i).getIdx(), users.get(i).get(i).getName()));
        }

        // 원하는 userList 정보들과 task 정보들이 저장된 taskListResponseDto
        List<TaskListResponseDto> taskListResponseDtos = new ArrayList<>();
        for (Task tasks : taskList) {
            taskListResponseDtos.add(new TaskListResponseDto(tasks, userList));
        }
        return taskListResponseDtos;
    }
    @Override
    public ProjectResponseDto updateProject(Long idx, ProjectRequestDto dto, Long userIdx)//실제로는 User user. 그리고 User.getIdx()해서 구현
    {
        // projectIdx로 createUserIdx를 찾는다. 그리고 비교
        Project project = findById(idx);
        Long createUserIdx = project.getCreateUserIdx();
        Orders order = ordersRepository.findById(dto.getOrdersIdx()).orElseThrow(() -> new EntityNotFoundException("기수 못찾음"));

        // 수정 조건 : 프로젝트 생성자만 할 수 있다.
        if (String.valueOf(createUserIdx).equals(String.valueOf(userIdx))) {
            //기수, 타입, 이름
            project.builder()
                    .type(dto.getProjectType())
                    .name(dto.getProjectName())
                    .orders(order).build();
            projectRepository.save(project); // update
        } else {
            System.out.println("생성한 유저만 수정할 수 있음");
            return null; // 예외처리하기
        }

        // 수정 후 바로 프로젝트 상세 정보를 보여주기 위함.
        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(project)
                .taskList(getTaskUser(idx)).build();
        return responseDto;
    }

    @Override
    public boolean deleteProject(Long idx, Long userIdx) //실제로는 User user. 그리고 User.getIdx()해서 구현
    {
        // projectIdx로 createUserIdx를 찾는다. 그리고 비교
        Project project = findById(idx);
        Long createUserIdx = project.getCreateUserIdx();

        // 수정 조건 : 프로젝트 생성자만 할 수 있다.
        if (String.valueOf(createUserIdx).equals(String.valueOf(userIdx))) {
            projectRepository.delete(project); // delete
        } else {
            System.out.println("생성한 유저만 삭제할 수 있음");
            return false; // 예외처리하기
        }
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectResponseDto getProject(Long idx) {
        Project project = findById(idx);

        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(project)
                .taskList(getTaskUser(idx)).build();
        return responseDto;
    }

    @Override
    public boolean setFinishedProject(Long idx, FinishProjectRequestDto dto) {
        // 예시
        Project findProject = findById(idx);

        if (findProject.getFinalCheck() == Mark.Y) {
            return false; // 추후 수정
        }

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

    // userList
    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> getUserListInProject(Long idx){
       return null;

    }
}
