package blog.common.util;

import blog.api.category.model.entity.Category;
import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanUtils extends org.springframework.beans.BeanUtils {

  public static <T> T copyProperties(Object sources, Class<T> clazz) {

    return copyProperties(sources, clazz, new String[0]);
  }

  public static <T> T copyProperties(Object sources, Class<T> clazz, String[] ignoreProperties) {

    T t = null;
    try {
      t = clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }

    assert t != null;
    copyProperties(sources, t, ignoreProperties);

    return t;
  }

  public static <T> List<T> copyProperties(List<?> sources, Class<T> clazz) {

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

  public static <T> T copyNullableProperties(Object source, Class<T> clazz) {
    return copyProperties(source, clazz, BeanUtils.getNullPropertyNames(source));
  }

  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
    return Stream.of(wrappedSource.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
        .toArray(String[]::new);
  }
}
