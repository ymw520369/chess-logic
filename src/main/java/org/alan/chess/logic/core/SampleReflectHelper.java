package org.alan.chess.logic.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/7/10.
 *
 * @author Alan
 * @since 1.0
 */
public class SampleReflectHelper {
    public static final Logger log = LoggerFactory.getLogger(SampleReflectHelper.class);

    public static List<Sample> resolveSample(Class<?> clazz,
                                             List<String> attributeNames, List<List<String>> attributeValues) {
        List<Sample> samples = new ArrayList<Sample>(attributeValues.size());
        Sample sample = null;
        int n = 4;
        for (List<String> values : attributeValues) {
            n++;
            try {
                sample = (Sample) clazz.newInstance();
            } catch (Exception e) {
                log.warn("", e);
            }
            if (values.size() != attributeNames.size()) {
                log.warn("数据异常，值列表长度与标题列表长度不一致" + ",class=" + clazz.getName()
                        + ",第 " + n + " 行数据异常[" + values + "]");
                throw new RuntimeException();
            }
            for (int i = 0; i < attributeNames.size(); i++) {
                String attributeName = attributeNames.get(i).trim();
                String attributeValue = values.get(i).trim();
                if (attributeNames.get(i).isEmpty() || values.get(i).isEmpty()) {
                    continue;
                }
                if (!set(sample, attributeName, attributeValue)) {
                    log.warn("配置读取失败，className={},fieldName={},value={}", clazz.getName(), attributeName, attributeValue);
                }
            }
            if (sample.sid > 0) {
                samples.add(sample);
            }
        }
        return samples;
    }

    private static boolean set(Object instance, String fieldName, String value) {
        try {
            Field field = instance.getClass().getField(fieldName);
            Class<?> fieldClazz = field.getType();
            if (fieldClazz == List.class) {
                Type type = field.getGenericType();
                List list = (List) field.get(instance);
                if (list == null) {
                    list = new ArrayList();
                    field.set(instance, list);
                }
                Object vobj = value;
                // 如果是泛型参数的类型
                if (type instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) type;
                    //得到泛型里的class类型对象
                    Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                    vobj = parseValue(genericClazz, value);
                }

                list.add(vobj);
            } else {
                Object vobj = parseValue(fieldClazz, value);
                field.set(instance, vobj);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("", e);
        }
        return false;
    }

    public static Object parseValue(Type fieldClazz, String value) {
        if (fieldClazz == String.class) {
            return value;
        } else if (fieldClazz == int.class || fieldClazz == Integer.class) {
            return Double.valueOf(value).intValue();
        } else if (fieldClazz == byte.class || fieldClazz == Byte.class) {
            return Double.valueOf(value).byteValue();
        } else if (fieldClazz == boolean.class || fieldClazz == Boolean.class) {
            if (value == null || value.isEmpty() || "false".equalsIgnoreCase(value) || "0.0".equals(value) || "0".equals(value)) {
                return false;
            } else {
                return true;
            }
        } else if (fieldClazz == short.class || fieldClazz == Short.class) {
            return Double.valueOf(value).shortValue();
        } else if (fieldClazz == long.class || fieldClazz == Long.class) {
            return Double.valueOf(value).longValue();
        } else if (fieldClazz == float.class || fieldClazz == Float.class) {
            return Float.valueOf(value);
        } else if (fieldClazz == double.class || fieldClazz == Double.class) {
            return Double.valueOf(value);
        }
        return value;
    }
}
