package com.yapp.web1.converter;

import com.yapp.web1.domain.VO.UrlType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UrlTypeAttributeConverter implements AttributeConverter<UrlType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UrlType attribute) {
        switch (attribute){
            case TOOL: return 0;
            case FIRST: return 1;
            case SECOND: return 2;
            case HOME: return 3;
            case THIRD: return 4;
        }
        return -1;
    }

    @Override
    public UrlType convertToEntityAttribute(Integer dbData) {
        switch (dbData){
            case 0: return UrlType.TOOL;
            case 1: return UrlType.FIRST;
            case 2: return UrlType.SECOND;
            case 3: return UrlType.HOME;
            case 4: return UrlType.THIRD;
        }
        return null;
    }
}
