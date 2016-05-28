package ru.tease.pick;

import ru.tease.pick.utils.CopyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pick<T> {

    private List<String> fieldsForCopy = new ArrayList<>();
    private List<String> ignoredFields = new ArrayList<>();
    private boolean deepCopy = false;

    private T object;

    public Pick(T object) {
        this.object = object;
    }

    public T pick() {
        return CopyUtils.copy(object, fieldsForCopy, ignoredFields, deepCopy);
    }


    public Pick<T> add(String ... fields) {
        fieldsForCopy.addAll(Arrays.asList(fields));
        return this;
    }

    public Pick<T> ignore(String ... fields) {
        ignoredFields.addAll(Arrays.asList(fields));
        return this;
    }

    public Pick<T> deepCopy(boolean deepCopy) {
        this.deepCopy = deepCopy;
        return this;
    }
}
