package Servicios;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;

public class UnionFind
{
    /**
     * Sets of vertices. 
     */
    private HashMap<Integer, HashSet<Integer>> sets;
    
    /**
     * Vertices with thier associated set. 
     */
    private HashMap<Integer, Integer> associatedSet;
    
    /**
     * The number of disjoint sets.
     */
    private int num;
    
    /**
     * Create a disjoint set for each vertex in vertices.
     * 
     * @param vertices
     */
    public UnionFind(Set<Integer> vertices) {
        if (vertices.isEmpty())
            throw new IllegalArgumentException("Vertices is empty.");
        
        sets = new HashMap<>(vertices.size());
        associatedSet = new HashMap<>(vertices.size());
        for (Integer v : vertices) {
            sets.put(v, new HashSet<>(vertices.size()));
            sets.get(v).add(v);
            associatedSet.put(v, v);
        }

        num = vertices.size();
    }
    
    /**
     * Find set of vertex v
     * 
     * @param v
     * @return
     */
    public int find(int v) {
        Integer res = associatedSet.get(v);
        if (res != null) {
            return res;
        } else {
            throw new NoSuchElementException("Invalid element.");
        }
    }
    
    /**
     * Merge set containing u with the one containing v.
     * 
     * @param u
     * @param v
     * @return the representative of union 
     */
    public int union(int u, int v) {
        int setV = find(v);
        int setU = find(u);
        
        if (setU == setV)
            return setU;
        
        if (sets.get(setU).size() < sets.get(setV).size()) {
            for (Integer vertex : sets.get(setU)) {
                sets.get(setV).add(vertex);
                associatedSet.put(vertex, setV);
            }
            sets.remove(setU);
            num--;
            return setV;
        } else {
            for (Integer vertex : sets.get(setV)) {
                sets.get(setU).add(vertex);
                associatedSet.put(vertex, setU);
            }
            sets.remove(setV);
            num--;
            return setU;
        }
    }
    
    public int numberOfSets() {
        return num;
    }
}