package ru.tease.pick.utils;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class CopyUtils {

    static List<String> split(String field) {
        if (field != null) {
            return Arrays.asList(field.split("\\."));
        }
        return Collections.singletonList("");
    }

    static Tree createPath(List<String> path) {
        if (path == null || path.isEmpty()) {
            return null;
        } else if (path.size() == 1) {
            return new Tree(path.get(0));
        } else {
            return new Tree(path.get(0)).add(createPath(path.subList(1, path.size())));
        }
    }

    static Tree createPattern(List<String> fields) {
        return createPattern(new Tree("root"), fields);
    }

    private static Tree createPattern(Tree tree, List<String> fields) {
        if (fields == null) {
            return tree;
        }
        fields.stream().map(CopyUtils::split).forEach(_fields -> tree.add(createPath(_fields)));
        return tree;
    }

    @SuppressWarnings("unchecked")
    static Method getMethod(Class clazz, String name, Class<?>... params) {
        try {
            return clazz.getMethod(name, params);
        } catch (NoSuchMethodException e) {
         /*e.printStackTrace();*/
        }
        return null;
    }

    static Method getGetter(Class clazz, String name) {
        Method method = getMethod(clazz, "get" + name.substring(0, 1).toUpperCase() + name.substring(1));
        if (method == null) {
            method = getMethod(clazz, "is" + name.substring(0, 1).toUpperCase() + name.substring(1));
            if (method == null) {
                method = getMethod(clazz, "get" + name);
                if (method == null) {
                    throw new RuntimeException();
                }
            }
        }
        return method;
    }

    static Method getSetter(Class clazz, String name) {
        Method method =  getMethod(clazz, "set" + name.substring(0, 1).toUpperCase() + name.substring(1), getGetter(clazz, name).getReturnType());
        if (method == null) {
            method = getMethod(clazz, "set" + name, getGetter(clazz, name).getReturnType());
            if (method == null) {
                throw new RuntimeException();
            }
        }
        return method;
    }

    static Object invoke(Method method, Object object, Object... params) {
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }


    static Object removeFields(Object object, Tree patternForIgnore) {
        try {
            if (patternForIgnore.getNodes().isEmpty()) {
                return null;
            }

            Class clazz = object.getClass();

            patternForIgnore.getNodes().stream().forEach(tree -> {
                Method setter = getSetter(clazz, tree.getName());
                Method getter = getGetter(clazz, tree.getName());
                Object result = removeFields(invoke(getter, object), patternForIgnore.get(tree.getName()).orElse(new Tree("#empty")));
                invoke(setter, object, result);
            });

            return object;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    static Object copy(Object object, Tree patternForCopy, Tree patternForIgnore, boolean deepCopy) {
        try {
            if (object == null) {
                return null;
            }
            if (patternForIgnore.getNodes().isEmpty() &&
                    !Objects.equals("root", patternForIgnore.getName()) &&
                    Objects.equals(patternForCopy.getName(), patternForIgnore.getName())) {

                return null;
            }
            if (object instanceof List) {
                return ((List) object).stream()
                        .map(o -> copy(o, patternForCopy, patternForIgnore, deepCopy))
                        .collect(toList());
            }

            if (patternForCopy.getNodes().isEmpty()) {
                if (patternForIgnore.getNodes().isEmpty()) {
                    return deepCopy ? deepClone(object) : object;
                } else {
                    return removeFields(deepClone(object), patternForIgnore);
                }
            }

            Class clazz = object.getClass();

            if (clazz.isEnum()) {
                return object;
            }

            Object obj =  clazz.newInstance();

            patternForCopy.getNodes().stream().forEach(tree -> {
                Method setter = getSetter(clazz, tree.getName());
                Method getter = getGetter(clazz, tree.getName());
                Object result = copy(
                        invoke(getter, object),
                        tree,
                        patternForIgnore.get(tree.getName()).orElse(new Tree("#empty")),
                        deepCopy);
                invoke(setter, obj, result);
            });

            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T copy(T object, List<String> fieldsForCopy, List<String> ignoredFields, boolean deepCopy) {
        return (T) copy(object, createPattern(fieldsForCopy), createPattern(ignoredFields), deepCopy);
    }
}
