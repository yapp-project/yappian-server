package com.yapp.web1.converter;

import com.yapp.web1.domain.VO.TaskStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TaskStatusAttributeConverter implements AttributeConverter<TaskStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskStatus attribute) {
        switch (attribute){
            case DOING: return 0;
            case DONE: return 1;
            case HOLD: return 2;
        }
        return -1;
    }

    @Override
    public TaskStatus convertToEntityAttribute(Integer dbData) {
        switch (dbData){
            case 0: return TaskStatus.DOING;
            case 1: return TaskStatus.DONE;
            case 2: return TaskStatus.HOLD;
        }
        return null;
    }
}
