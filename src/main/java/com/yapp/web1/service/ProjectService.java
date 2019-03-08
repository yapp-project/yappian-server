package com.yapp.web1.service;

import com.yapp.web1.domain.Orders;
import com.yapp.web1.domain.Project;
import com.yapp.web1.repository.OrderRepository;
import com.yapp.web1.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public long insertProject(Project p, long userIdx){
        Project project = new Project();
        project.setType(p.getType());//프로젝트 타입
        project.setName(p.getName());//프로젝트 이름
        project.setOrders(new Orders());//프로젝트 기수
        project.getOrders().setIdx(p.getOrders().getIdx());
        project.getOrders().setNumber(p.getOrders().getNumber());
        project.setCreateUserIdx(userIdx);// 프로젝트 생성자Idx
        projectRepository.save(project);
        System.out.println("프로젝트 생성됨");
        return project.getIdx();
    }
}
