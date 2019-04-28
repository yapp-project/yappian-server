package com.yapp.web1.service.impl;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.domain.User;
import com.yapp.web1.domain.VO.Mark;
import com.yapp.web1.dto.req.FinishProjectRequestDto;
import com.yapp.web1.dto.req.ProjectRequestDto;
import com.yapp.web1.dto.res.FinishProjectResponseDto;
import com.yapp.web1.dto.res.ProjectResponseDto;
import com.yapp.web1.dto.res.UserResponseDto;
import com.yapp.web1.exception.Common.NoPermissionException;
import com.yapp.web1.exception.Common.NotFoundException;
import com.yapp.web1.repository.OrdersRepository;
import com.yapp.web1.repository.ProjectRepository;
import com.yapp.web1.repository.UserRepository;
import com.yapp.web1.service.FileService;
import com.yapp.web1.service.ProjectService;
import com.yapp.web1.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * ProjectService 구현 클래스
 *
 * @author Dakyung Ko
 * @author Jihye Kim
 * @version 1.2
 * @since 0.0.4
 */

@Service
@Transactional
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final UrlService urlService;
    private final FileService fileService;


    // parsUser
    private List<UserResponseDto> parsUser(Project project){
        Set<User> userSet = project.getUserList();
        User u;

        List<UserResponseDto> userResponseDtos =new ArrayList<>();

        Iterator<User> it = userSet.iterator();

        while(it.hasNext()){
            u = it.next();
            userResponseDtos.add(new UserResponseDto(u.getIdx(),u.getName()));
        }
        return userResponseDtos;
    }

    // userSet
    private Set<User> userSet(Long userIdx){
        Set<User> userSet = new HashSet<>();
        userSet.add(findUserById(userIdx));;

        return userSet;
    }

    // join 하기 - user
    private void joinedProject(Project project, Long userIdx){
        User user = findUserById(userIdx);

        user.getJoinedProjects().add(project);
    }

    // Orders findByOrdersId
    @Transactional(readOnly = true)
    @Override
    public Orders findOrdersById(Long idx){
        return ordersRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 기수 없음"));
    }

    // User findByUserId
    @Transactional(readOnly = true)
    @Override
    public User findUserById(Long idx){
        return userRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 유저 없음"));
    }

    // project findById
    @Transactional(readOnly = true)
    @Override
    public Project findById(Long idx) {
        return projectRepository.findById(idx).orElseThrow(() -> new NotFoundException("해당 프로젝트 없음"));
    }

    // user 권한 검사
    @Transactional(readOnly = true)
    @Override
    public void checkUserPermission(List<UserResponseDto> userList, Long userIdx) {
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
                .orders(findOrdersById(dto.getOrdersIdx())).build();

        projectRepository.save(project); // create

        // user쪽 join
        joinedProject(project, userIdx);

        // 생성후 바로 프로젝트 상세 정보를 보여주기 위함.
        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(project)
                .userList(parsUser(project))
                .urlList(new ArrayList<>()).build(); // 생성할 때는 빈 Url 목록
        return responseDto;
    }

    //update project
    @Override
    public ProjectResponseDto updateProject(Long idx, ProjectRequestDto dto, Long userIdx)
    {
        Project project = findById(idx);

        // 수정 조건 : 프로젝트 조인된 사람만 수정할 수 있다.
        checkUserPermission(getUserListInProject(idx), userIdx);

        project.setOrders(findOrdersById(dto.getOrdersIdx()));
        project.setName(dto.getProjectName());
        project.setType(dto.getProjectType());
        project.setPassword(dto.getPassword());

        projectRepository.save(project); // update

        // 수정 후 바로 프로젝트 상세 정보를 보여주기 위함.
        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(project)
                .userList(parsUser(project))
                .urlList(urlService.getUrl(idx)).build();
        return responseDto;
    }

    //delete project
    @Override
    public void deleteProject(Long idx, Long userIdx) //User user
    {
        Project findProject = findById(idx); // 해당 프로젝트 존재 여부 체크 위함

        // 수정 조건 : 프로젝트 조인된 사람만 수정할 수 있다.
        checkUserPermission(getUserListInProject(idx), userIdx);


        // parsUser 삭제
        List<UserResponseDto> userResponseDtos = parsUser(findProject);
        User user=null;

        for(int i=0; i<userResponseDtos.size(); ++i){
            user = findUserById(userResponseDtos.get(i).getUserIdx());
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
        Project project = findById(idx);

        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .project(project)
                .userList(parsUser(project))
                .urlList(urlService.getUrl(idx)).build();
        return responseDto;
    }

    // join project
    @Override
    public void joinProject(Long projectIdx, Long userIdx){

        User user = findUserById(userIdx);
        Project project = findById(projectIdx);
        joinedProject(project,user.getIdx());

    }

    // userList
    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> getUserListInProject(Long idx) {

        Project project = findById(idx);

        return parsUser(project);
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

}
