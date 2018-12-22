package blog.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cyclamen on 22/12/2018
 */
public class JacksonUtils {

    public static <T> String toForceJson(T model) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
