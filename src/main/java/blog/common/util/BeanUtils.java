package blog.common.util;

import java.util.ArrayList;
import java.util.List;

public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <T> List<T> copyProperty(List<?> sources, Class<T> clazz) {

        List<T> results = new ArrayList<>();
        sources.forEach(source -> {
            T object = null;
            try {
                object = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            assert object != null;
            copyProperties(source, object);
            results.add(object);
        });

        return results;
    }
}
