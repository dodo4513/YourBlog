package blog.common.etc;

import blog.common.util.JacksonUtils;

import javax.persistence.AttributeConverter;

/**
 * @author cyclamen on 23/12/2018
 */
public class MapToJsonConverter implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        return JacksonUtils.toForceJson(attribute);

    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        return JacksonUtils.toForceMap(dbData);
    }
}
