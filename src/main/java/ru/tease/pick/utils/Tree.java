package ru.tease.pick.utils;


import java.util.*;

public class Tree {
    private List<Tree> nodes = new ArrayList<>();
    private String name;

    public Tree(String name) {
        this.name = name;
    }

    public Optional<Tree> get(String name) {
        return nodes.stream().filter(node -> Objects.equals(name, node.name)).findFirst();
    }

    public Tree add(Tree ... nodes) {
        return this.add(Arrays.asList(nodes));
    }

    public Tree add(List<Tree> nodes) {
        if (nodes == null) {
            return this;
        }
        nodes.forEach(node -> {
            Optional<Tree> n = this.nodes.stream().filter(_n -> Objects.equals(_n.name, node.name)).findFirst();
            if (n.isPresent()) {
                n.get().add(node.nodes);
            } else {
                this.nodes.add(node);
            }
        });
        return this;
    }

    List<Tree> getNodes() {
        return nodes;
    }

    String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tree tree = (Tree) o;
        return nodes != null ? nodes.equals(tree.nodes) : tree.nodes == null && name.equals(tree.name);
    }

    @Override
    public int hashCode() {
        int result = nodes != null ? nodes.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

    private String print(String padding) {
        StringBuilder sb = new StringBuilder();
        sb.append(padding).append(name).append("\n");
        this.nodes.stream().forEach(n -> sb.append(n.print(padding + ".")));
        return sb.toString();
    }

    @Override
    public String toString() {
        return print("");
    }
}
