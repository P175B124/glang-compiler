package edu.ktu.glang.compiler;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Integer> table;

    public SymbolTable() {
        table = new HashMap<>();
    }

    public void put(String name, Integer value) {
        table.put(name, value);
    }

    public Integer get(String name) {
        return table.get(name);
    }

    public boolean contains(String name) {
        return table.containsKey(name);
    }

    public int size() {
        return table.size();
    }
}
