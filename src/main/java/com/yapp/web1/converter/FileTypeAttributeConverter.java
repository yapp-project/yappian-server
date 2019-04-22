package com.yapp.web1.converter;

import com.yapp.web1.domain.VO.FileType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FileTypeAttributeConverter implements AttributeConverter<FileType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(FileType attribute) {
        switch (attribute){
            case IMAGE: return 0;
            case PDF: return 1;
        }
        return -1;
    }

    @Override
    public FileType convertToEntityAttribute(Integer dbData) {
        switch (dbData){
            case 0 : return FileType.IMAGE;
            case 1 : return FileType.PDF;
        }
        return null;
    }
}
