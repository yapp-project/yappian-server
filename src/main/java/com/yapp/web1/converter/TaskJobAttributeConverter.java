package com.yapp.web1.converter;

import com.yapp.web1.domain.VO.TaskJob;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TaskJobAttributeConverter implements AttributeConverter<TaskJob, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskJob attribute) {
        switch (attribute){
            case PLANNER: return 0;
            case DESIGNER: return 1;
            case FRONT: return 2;
            case BACK: return 3;
        }
        return -1;
    }

    @Override
    public TaskJob convertToEntityAttribute(Integer dbData) {
        switch (dbData){
            case 0: return TaskJob.PLANNER;
            case 1: return TaskJob.DESIGNER;
            case 2: return TaskJob.FRONT;
            case 3: return TaskJob.BACK;
        }
        return null;
    }
}
