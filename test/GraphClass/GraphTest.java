package GraphClass;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        createGraph();
    }

    @AfterEach
    void tearDown() {
        graph.clearGraph();
    }

    @Test
    public void addVertex(){
        graph.clearGraph();
        Assertions.assertTrue(graph.addVertex("1"));
        Assertions.assertFalse(graph.addVertex("2"));
        Assertions.assertTrue(graph.addVertex("2", "1", 10, true));
        Assertions.assertFalse(graph.addVertex("2", "1", 20, true));
    }

    @Test
    public void getInArcs() {
        testMap.put("1", 10);
        testMap.put("3", 20);
        Assertions.assertEquals(testMap, graph.getInArcs("2")
        );
        testMap.clear();

        testMap.put("1", 5);
        Assertions.assertEquals(testMap, graph.getInArcs("3")
        );
        testMap.clear();

        Assertions.assertEquals(new HashMap<>(), graph.getInArcs("Несуществующая")
        );
    }

    @Test
    public void getOutArcs() {
        testMap.put("2", 10);
        testMap.put("3", 5);
        Assertions.assertEquals(testMap, graph.getOutArcs("1")
        );
        testMap.clear();

        testMap.put("2", 20);
        Assertions.assertEquals(testMap, graph.getOutArcs("3")
        );
        testMap.clear();

        Assertions.assertEquals(new HashMap<>(), graph.getOutArcs("Несуществующая")
        );
    }

    @Test
    public void changeName() {
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

        Assertions.assertFalse(graph.changeName("Несуществующая", "Какое-то имя"));

        Assertions.assertFalse(graph.changeName("Ростов", "Минск"));
    }

    @Test
    public void changeArcSize() {
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

        Assertions.assertFalse(graph.changeArcSize("Несуществующая", "Тоже не существующая", 10));
    }

    @Test
    public void delVertex() {
        Assertions.assertTrue(graph.delVertex("2"));
        Assertions.assertFalse(graph.delVertex("Несуществующая"));

        testMap.put("3", 5);
        Assertions.assertEquals(testMap, graph.getOutArcs("1")
        );
        testMap.clear();

        testMap.put("1", 5);
        Assertions.assertEquals(testMap, graph.getInArcs("3")
        );
        testMap.clear();

        Assertions.assertEquals(new HashMap<>(), graph.getInArcs("2")
        );
    }

    @Test
    public void delArc() {
        graph.delArc("1", "2");

        testMap.put("3", 5);
        Assertions.assertEquals(testMap, graph.getOutArcs("1")
        );
        testMap.clear();

        testMap.put("3", 20);
        Assertions.assertEquals(testMap, graph.getInArcs("2")
        );
        testMap.clear();

        Assertions.assertFalse(graph.delArc("Несуществующая", "3"));
    }

    @Test
    public void delWithSplit() {
        graph.delArc("2", "3");
        graph.addVertex("3", "2", 20, false);
        graph.addVertex("4", "3", 10, true);
        graph.addVertex("5", "4", 10, false);
        graph.addVertex("6", "4", 10, true);
        graph.addVertex("7", "6", 10, true);
        graph.addArc("5", "6", 10);
        graph.delArc("4", "3");

        Assertions.assertFalse(graph.delArc("5", "6"));
        Assertions.assertFalse(graph.changeName("7", "7ка не существует"));
    }
}
