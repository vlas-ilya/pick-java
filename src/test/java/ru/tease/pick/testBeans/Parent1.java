package ru.tease.pick.testBeans;

import java.io.Serializable;

public class Parent1 implements Serializable {

    private SomeClass someClass;
    private Integer integer;
    private String string;

    public SomeClass getSomeClass() {
        return someClass;
    }

    public void setSomeClass(SomeClass someClass) {
        this.someClass = someClass;
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

        Parent1 parent1 = (Parent1) o;

        if (someClass != null ? !someClass.equals(parent1.someClass) : parent1.someClass != null) return false;
        if (integer != null ? !integer.equals(parent1.integer) : parent1.integer != null) return false;
        return string != null ? string.equals(parent1.string) : parent1.string == null;

    }

    @Override
    public int hashCode() {
        int result = someClass != null ? someClass.hashCode() : 0;
        result = 31 * result + (integer != null ? integer.hashCode() : 0);
        result = 31 * result + (string != null ? string.hashCode() : 0);
        return result;
    }
}
