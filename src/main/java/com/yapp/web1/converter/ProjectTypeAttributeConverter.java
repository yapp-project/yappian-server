package com.yapp.web1.converter;

import com.yapp.web1.domain.VO.ProjectType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProjectTypeAttributeConverter implements AttributeConverter<ProjectType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProjectType attribute) {
        switch (attribute){
            case WEB: return 0;
            case ANDROID: return 1;
            case IOS: return 2;
        }
        return -1;
    }

    @Override
    public ProjectType convertToEntityAttribute(Integer dbData) {
        switch (dbData){
            case 0: return ProjectType.WEB;
            case 1: return ProjectType.ANDROID;
            case 2: return ProjectType.IOS;
        }
        return null;
    }
}
