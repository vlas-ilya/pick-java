package ru.tease.pick.testBeans;

public class ChildSomeClass extends SomeClass {
    private boolean bool1;
    private boolean bool2;

    public boolean isBool1() {
        return bool1;
    }

    public void setBool1(boolean bool1) {
        this.bool1 = bool1;
    }

    public boolean isBool2() {
        return bool2;
    }

    public void setBool2(boolean bool2) {
        this.bool2 = bool2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ChildSomeClass that = (ChildSomeClass) o;

        if (bool1 != that.bool1) return false;
        return bool2 == that.bool2;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bool1 ? 1 : 0);
        result = 31 * result + (bool2 ? 1 : 0);
        return result;
    }
}
