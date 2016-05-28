package ru.tease.pick.testBeans;

public class Child1Child1 extends Child1 {

    boolean bool;

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Child1Child1 that = (Child1Child1) o;

        return bool == that.bool;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bool ? 1 : 0);
        return result;
    }
}
