package ru.tease.pick;

import org.junit.Test;
import ru.tease.pick.testBeans.Child1;
import ru.tease.pick.testBeans.ChildSomeClass;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PickTest {

    private Child1 getChild1() {
        Child1 child1 = new Child1();
        child1.setInteger(5);
        child1.setString("5");
        child1.setStrings(Arrays.asList("a", "b", "c"));
        ChildSomeClass childSomeClass = new ChildSomeClass();
        childSomeClass.setIntegers(Arrays.asList(1, 2, 3));
        childSomeClass.setString("2");
        childSomeClass.setBool1(true);
        childSomeClass.setInteger(2);
        childSomeClass.setBool2(true);
        child1.setSomeClass(childSomeClass);
        return child1;
    }

    private Child1 getResultChild1() {
        Child1 result = new Child1();
        result.setInteger(5);
        result.setStrings(Arrays.asList("a", "b", "c"));
        ChildSomeClass childSomeClassResult = new ChildSomeClass();
        childSomeClassResult.setIntegers(Arrays.asList(1, 2, 3));
        childSomeClassResult.setBool1(true);
        childSomeClassResult.setString("2");
        result.setSomeClass(childSomeClassResult);
        return result;
    }

    @Test
    public void copyTest() {
        List<Child1> child1 = Arrays.asList(getChild1(), getChild1(), getChild1());
        List<Child1> result = Arrays.asList(getResultChild1(), getResultChild1(), getResultChild1());

        List<Child1> realResult = new Pick<>(child1)
                .add("integer", "someClass.string", "someClass.integers", "someClass.bool1", "strings")
                .pick();

        assertEquals(realResult, result);
    }

}