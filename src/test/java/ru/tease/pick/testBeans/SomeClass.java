package ru.tease.pick.testBeans;

import java.io.Serializable;
import java.util.List;

public class SomeClass implements Serializable {
    private List<Integer> integers;
    private Integer integer;
    private String string;

    public List<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(List<Integer> integers) {
        this.integers = integers;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SomeClass someClass = (SomeClass) o;

        if (integers != null ? !integers.equals(someClass.integers) : someClass.integers != null) return false;
        if (integer != null ? !integer.equals(someClass.integer) : someClass.integer != null) return false;
        return string != null ? string.equals(someClass.string) : someClass.string == null;

    }

    @Override
    public int hashCode() {
        int result = integers != null ? integers.hashCode() : 0;
        result = 31 * result + (integer != null ? integer.hashCode() : 0);
        result = 31 * result + (string != null ? string.hashCode() : 0);
        return result;
    }
}
