package com.yapp.web1.converter;

import com.yapp.web1.domain.VO.Mark;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MarkAttributeConverter implements AttributeConverter<Mark, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Mark attribute) {
        if (attribute == null)
            return null;
        return (attribute == Mark.N ? 0 : 1);
    }

    @Override
    public Mark convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;
        return (dbData == 0 ? Mark.N : Mark.Y);
    }
}
