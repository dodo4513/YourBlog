package blog.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author cyclamen on 22/12/2018
 */
public class JacksonUtils {

  public static <T> String toForceJson(T model) {
    ObjectMapper objectMapper = getObjectMapper();
    try {
      return objectMapper.writeValueAsString(model);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T toForceModel(String src, Class<T> clazz) {
    ObjectMapper mapper = getObjectMapper();
    try {
      return mapper.readValue(src, clazz);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> List<T> toForceList(String src, Class<T> clazz) {
    ObjectMapper mapper = getObjectMapper();
    try {
      return mapper
          .readValue(src, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Map<String, String> toForceMap(String src) {
    try {
      if (src == null) {
        return null;
      }
      ObjectMapper mapper = getObjectMapper();

      return mapper.readValue(src, new TypeReference<Map<String, String>>() {
      });
    } catch (Exception e) {
      e.printStackTrace();

      return null;
    }
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    return objectMapper;
  }


}
