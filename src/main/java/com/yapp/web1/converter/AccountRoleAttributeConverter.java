package com.yapp.web1.converter;

import com.yapp.web1.domain.VO.AccountRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AccountRoleAttributeConverter implements AttributeConverter<AccountRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AccountRole attribute) {
        switch (attribute){
            case ADMIN: return 0;
            case USER: return 1;
        }
        return -1;
    }

    @Override
    public AccountRole convertToEntityAttribute(Integer dbData) {
        switch (dbData){
            case 0: return AccountRole.ADMIN;
            case 1: return AccountRole.USER;
        }
        return null;
    }
}
