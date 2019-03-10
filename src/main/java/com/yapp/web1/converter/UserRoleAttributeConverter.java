package com.yapp.web1.converter;

import com.yapp.web1.domain.VO.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleAttributeConverter implements AttributeConverter<UserRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserRole attribute) {
        switch (attribute){
            case ADMIN: return 0;
            case USER: return 1;
        }
        return -1;
    }

    @Override
    public UserRole convertToEntityAttribute(Integer dbData) {
        switch (dbData){
            case 0: return UserRole.ADMIN;
            case 1: return UserRole.USER;
        }
        return null;
    }
}
