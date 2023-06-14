package efs.task.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClassInspector {

  public static Collection<String> getAnnotatedFields(final Class<?> type,
                                                      final Class<? extends Annotation> annotation) {

    Set<String> annotatedFields = new HashSet<>();

    Field[] fields = type.getDeclaredFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(annotation)) {
        annotatedFields.add(field.getName());
      }
    }

    return annotatedFields;
  }

  public static Collection<String> getAllDeclaredMethods(final Class<?> type) {

    Set<String> methodsNames = new HashSet<>();

    Method[] methods = type.getDeclaredMethods();

    for (Method method : methods) {
      methodsNames.add(method.getName());
    }

    for (Class<?> iface : type.getInterfaces()) {
      methods = iface.getMethods();
      for (Method method : methods) {
        methodsNames.add(method.getName());
      }
    }

    return methodsNames;
  }

  public static <T> T createInstance(final Class<T> type, final Object... args) throws Exception {
    Class<?>[] argTypes = Arrays.stream(args)
            .map(Object::getClass)
            .toArray(Class<?>[]::new);

    Constructor<T> constructor = type.getDeclaredConstructor(argTypes);
    constructor.setAccessible(true);

    return constructor.newInstance(args);
  }
}
