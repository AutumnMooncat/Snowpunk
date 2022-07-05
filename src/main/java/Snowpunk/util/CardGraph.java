package Snowpunk.util;

import java.util.ArrayList;

public class CardGraph {
    public final ArrayList<CardVertex> vertices = new ArrayList<>();

    public void addVertex(CardVertex v) {
        vertices.add(v);
    }

    public void addDependence(CardVertex from, CardVertex to) {
        from.addParent(to);
        to.addChild(from);
    }

    public void clear() {
        for (CardVertex v : vertices) {
            v.parents.clear();
            v.children.clear();
        }
        vertices.clear();
    }

    public int depth() {
        int x = 0;
        for (CardVertex v : vertices) {
            if (v.x > x) {
                x = v.x;
            }
        }
        return x;
    }
}
