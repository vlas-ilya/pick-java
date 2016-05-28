package ru.tease.pick.testBeans;

import java.util.List;

public class Child1 extends Parent1 {

    private List<String> strings;

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Child1 child1 = (Child1) o;

        return strings != null ? strings.equals(child1.strings) : child1.strings == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (strings != null ? strings.hashCode() : 0);
        return result;
    }
}
