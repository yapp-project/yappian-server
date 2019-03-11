package com.yapp.web1.converter;

import com.yapp.web1.domain.VO.Mark;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MarkAttributeConverter implements AttributeConverter<Mark, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Mark attribute) {
        switch (attribute){
            case N: return 0;
            case Y: return 1;
        }
        return -1;
    }

    @Override
    public Mark convertToEntityAttribute(Integer dbData) {
        switch (dbData){
            case 0: return Mark.N;
            case 1: return Mark.Y;
        }
        return null;
    }
}
