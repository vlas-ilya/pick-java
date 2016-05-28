package ru.tease.pick.utils;

import org.junit.Test;
import ru.tease.pick.testBeans.Child1;
import ru.tease.pick.testBeans.ChildSomeClass;
import ru.tease.pick.testBeans.Parent1;
import ru.tease.pick.testBeans.SomeClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class CopyUtilsTest {

    @Test
    public void splitTest() {
        assertEquals(CopyUtils.split("a.b.c.d"), Arrays.asList("a", "b", "c", "d"));
    }

    @Test
    public void splitEmptyTest() {
        assertEquals(CopyUtils.split(""), Collections.singletonList(""));
    }

    @Test
    public void splitEmpty2Test() {
        assertEquals(CopyUtils.split(null), Collections.singletonList(""));
    }

    @Test
    public void splitSingleTest() {
        assertEquals(CopyUtils.split("a"), Collections.singletonList("a"));
    }

    @Test
    public void createPathTest() {
        Tree tree = CopyUtils.createPath(Arrays.asList("a", "b", "c", "d"));
        Tree result = new Tree("a").add(new Tree("b").add(new Tree("c").add(new Tree("d"))));
        assertEquals(tree, result);
    }

    @Test
    public void createPathEmptyTest() {
        Tree tree = CopyUtils.createPath(null);
        assertEquals(tree, null);
    }

    @Test
    public void createPathEmpty2Test() {
        Tree tree = CopyUtils.createPath(new ArrayList<>());
        assertEquals(tree, null);
    }

    @Test
    public void createPathSingleTest() {
        Tree tree = CopyUtils.createPath(Collections.singletonList("a"));
        Tree result = new Tree("a");
        assertEquals(tree, result);
    }

    @Test
    public void createPatternTest() {
        Tree tree = CopyUtils.createPattern(Arrays.asList("a.b.c", "a.b.d", "a.c", "a.d.d", "b.c"));
        Tree result = new Tree("root").add(
                new Tree("a").add(
                        new Tree("b").add(
                                new Tree("c"),
                                new Tree("d")),
                        new Tree("c"),
                        new Tree("d").add(
                                new Tree("d"))),
                new Tree("b").add(
                        new Tree("c")));
        assertEquals(tree, result);
    }

    @Test
    public void copyWithoutIgnoredFieldsTest() {
        Parent1 parent1 = new Parent1();
        parent1.setInteger(5);
        parent1.setString("5");
        SomeClass someClass = new SomeClass();
        someClass.setIntegers(Arrays.asList(1, 2, 3));
        someClass.setInteger(2);
        someClass.setString("2");
        parent1.setSomeClass(someClass);

        Parent1 result = new Parent1();
        result.setInteger(5);
        SomeClass someClassResult = new SomeClass();
        someClassResult.setIntegers(Arrays.asList(1, 2, 3));
        someClassResult.setString("2");
        result.setSomeClass(someClassResult);

        Parent1 realResult = CopyUtils.copy(
                parent1,
                Arrays.asList("integer", "someClass.integers", "someClass.string"),
                Arrays.asList("")
        );

        assertEquals(realResult, result);
    }

    @Test
    public void copyWithIgnoredFieldsTest() {
        Parent1 parent1 = new Parent1();
        parent1.setInteger(5);
        parent1.setString("5");
        SomeClass someClass = new SomeClass();
        someClass.setIntegers(Arrays.asList(1, 2, 3));
        someClass.setInteger(2);
        someClass.setString("2");
        parent1.setSomeClass(someClass);

        Parent1 result = new Parent1();
        result.setInteger(5);
        SomeClass someClassResult = new SomeClass();
        someClassResult.setIntegers(Arrays.asList(1, 2, 3));
        someClassResult.setInteger(2);
        result.setSomeClass(someClassResult);

        Parent1 realResult = CopyUtils.copy(
                parent1,
                Arrays.asList("integer", "someClass"),
                Arrays.asList("someClass.string")
        );

        assertEquals(realResult, result);
    }

    @Test
    public void copyWithIgnoredFields2Test() {
        Parent1 parent1 = new Parent1();
        parent1.setInteger(5);
        parent1.setString("5");
        SomeClass someClass = new SomeClass();
        someClass.setIntegers(Arrays.asList(1, 2, 3));
        someClass.setInteger(2);
        someClass.setString("2");
        parent1.setSomeClass(someClass);

        Parent1 result = new Parent1();
        result.setInteger(5);
        SomeClass someClassResult = new SomeClass();
        someClassResult.setIntegers(Arrays.asList(1, 2, 3));
        result.setSomeClass(someClassResult);

        Parent1 realResult = CopyUtils.copy(
                parent1,
                Arrays.asList("integer", "someClass"),
                Arrays.asList("someClass.string", "someClass.integer")
        );

        assertEquals(realResult, result);
    }

    @Test
    public void copyWithInheritanceWithoutIgnoredFieldsTest() {
        Child1 child1 = new Child1();
        child1.setInteger(5);
        child1.setString("5");
        child1.setStrings(Arrays.asList("a", "b", "c"));
        ChildSomeClass childSomeClass = new ChildSomeClass();
        childSomeClass.setIntegers(Arrays.asList(1, 2, 3));
        childSomeClass.setInteger(2);
        childSomeClass.setString("2");
        childSomeClass.setBool1(true);
        childSomeClass.setBool2(true);
        child1.setSomeClass(childSomeClass);

        Child1 result = new Child1();
        result.setInteger(5);
        result.setStrings(Arrays.asList("a", "b", "c"));
        ChildSomeClass childSomeClassResult = new ChildSomeClass();
        childSomeClassResult.setIntegers(Arrays.asList(1, 2, 3));
        childSomeClassResult.setString("2");
        childSomeClassResult.setBool1(true);
        result.setSomeClass(childSomeClassResult);

        Parent1 realResult = CopyUtils.copy(
                child1,
                Arrays.asList("integer", "someClass.string", "someClass.integers", "someClass.bool1", "strings"),
                Arrays.asList("")
        );

        assertEquals(realResult, result);
    }

    private Child1 getChild1() {
        Child1 child1 = new Child1();
        child1.setInteger(5);
        child1.setString("5");
        child1.setStrings(Arrays.asList("a", "b", "c"));
        ChildSomeClass childSomeClass = new ChildSomeClass();
        childSomeClass.setIntegers(Arrays.asList(1, 2, 3));
        childSomeClass.setInteger(2);
        childSomeClass.setString("2");
        childSomeClass.setBool1(true);
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
        childSomeClassResult.setString("2");
        childSomeClassResult.setBool1(true);
        result.setSomeClass(childSomeClassResult);
        return result;
    }

    @Test
    public void copyArrayWithInheritanceWithoutIgnoredFieldsTest() {
        List<Child1> child1 = Arrays.asList(getChild1(), getChild1(), getChild1());
        List<Child1> result = Arrays.asList(getResultChild1(), getResultChild1(), getResultChild1());

        List<Child1> realResult = CopyUtils.copy(
                child1,
                Arrays.asList("integer", "someClass.string", "someClass.integers", "someClass.bool1", "strings"),
                Arrays.asList("")
        );

        assertEquals(realResult, result);
    }
}