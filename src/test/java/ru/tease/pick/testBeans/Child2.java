package ru.tease.pick.testBeans;

public class Child2 extends Parent1 {
    private ChildSomeClass childSomeClass;
    private Integer child2Integer;

    public ChildSomeClass getChildSomeClass() {
        return childSomeClass;
    }

    public void setChildSomeClass(ChildSomeClass childSomeClass) {
        this.childSomeClass = childSomeClass;
    }

    public Integer getChild2Integer() {
        return child2Integer;
    }

    public void setChild2Integer(Integer child2Integer) {
        this.child2Integer = child2Integer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Child2 child2 = (Child2) o;

        if (childSomeClass != null ? !childSomeClass.equals(child2.childSomeClass) : child2.childSomeClass != null)
            return false;
        return child2Integer != null ? child2Integer.equals(child2.child2Integer) : child2.child2Integer == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (childSomeClass != null ? childSomeClass.hashCode() : 0);
        result = 31 * result + (child2Integer != null ? child2Integer.hashCode() : 0);
        return result;
    }
}
