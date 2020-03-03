package GraphClass;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GraphTest {
    Graph graph = new Graph();
    Map<String, Integer> testMap = new HashMap();

    //            10
    //      1 ----------→ 2
    //      |            ↑
    //    5 |    20  ___/
    //      |    ___/
    //      ↓___/
    //      3
    //

    public void createGraph() {
        graph.addVertex("1");
        graph.addVertex("2", "1", 10, false);
        graph.addVertex("3", "2", 20, true);
        graph.addArc("1", "3", 5);
    }

    @Test
    public void getInArcs() {
        createGraph();

        testMap.put("1", 10);
        testMap.put("3", 20);
        Assertions.assertEquals(testMap, graph.getInArcs("2")
        );
        testMap.clear();

        testMap.put("1", 5);
        Assertions.assertEquals(testMap, graph.getInArcs("3")
        );
        testMap.clear();

        graph.clearGraph();
    }

    @Test
    public void getOutArcs() {
        createGraph();

        testMap.put("2", 10);
        testMap.put("3", 5);
        Assertions.assertEquals(testMap, graph.getOutArcs("1")
        );
        testMap.clear();

        testMap.put("2", 20);
        Assertions.assertEquals(testMap, graph.getOutArcs("3")
        );
        testMap.clear();

        graph.clearGraph();
    }

    @Test
    public void changeName() {
        createGraph();
        graph.changeName("1", "Ростов");
        graph.changeName("2", "Минск");
        graph.changeName("3", "Москва");

        testMap.put("Ростов", 10);
        testMap.put("Москва", 20);
        Assertions.assertEquals(testMap, graph.getInArcs("Минск")
        );
        testMap.clear();

        testMap.put("Минск", 10);
        testMap.put("Москва", 5);
        Assertions.assertEquals(testMap, graph.getOutArcs("Ростов")
        );
        testMap.clear();

        graph.clearGraph();
    }

    @Test
    public void changeArcSize() {
        createGraph();
        graph.changeArcSize("1", "2", 10);
        graph.changeArcSize("3", "2", 20);

        testMap.put("2", 10);
        testMap.put("3", 5);
        Assertions.assertEquals(testMap, graph.getOutArcs("1")
        );
        testMap.clear();

        testMap.put("2", 20);
        Assertions.assertEquals(testMap, graph.getOutArcs("3")
        );
        testMap.clear();

        graph.clearGraph();
    }

    @Test
    public void delVertex() {
        createGraph();
        graph.delVertex("2");

        testMap.put("3", 5);
        Assertions.assertEquals(testMap, graph.getOutArcs("1")
        );
        testMap.clear();

        testMap.put("1", 5);
        Assertions.assertEquals(testMap, graph.getInArcs("3")
        );
        testMap.clear();

        Assertions.assertThrows(NullPointerException.class, () -> {
            graph.getInArcs("2");
        });

        graph.clearGraph();
    }

    @Test
    public void delArc() {
        createGraph();
        graph.delArc("1", "2");

        testMap.put("3", 5);
        Assertions.assertEquals(testMap, graph.getOutArcs("1")
        );
        testMap.clear();

        testMap.put("3", 20);
        Assertions.assertEquals(testMap, graph.getInArcs("2")
        );
        testMap.clear();

        graph.clearGraph();
    }
}
