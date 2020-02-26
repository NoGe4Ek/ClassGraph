package GraphClass;

import GraphClass.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GraphTest {
    Graph graph = new Graph();
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
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addArc("1", "2", 10);
        graph.addArc("1", "3", 5);
        graph.addArc("3", "2", 20);
    }

    @Test
    public void getInArcs() {
        createGraph();
        Assertions.assertEquals("1. Направлена из вершины 1, длина 10\n" +
                        "2. Направлена из вершины 3, длина 20",
                graph.getInArcsInStr("2")
        );
        Assertions.assertEquals("1. Направлена из вершины 1, длина 5",
                graph.getInArcsInStr("3")
        );
        graph.clearGraph();
    }

    @Test
    public void getOutArcs() {
        createGraph();
        Assertions.assertEquals("1. Направлена в вершину 2, длина 10\n" +
                                         "2. Направлена в вершину 3, длина 5",
                graph.getOutArcsInStr("1")
        );
        Assertions.assertEquals("1. Направлена в вершину 2, длина 20",
                graph.getOutArcsInStr("3")
        );
        graph.clearGraph();
    }

    @Test
    public void changeName() {
        createGraph();
        graph.changeName("1", "Ростов");
        graph.changeName("2", "Минск");
        graph.changeName("3", "Москва");
        Assertions.assertEquals("1. Направлена из вершины Ростов, длина 10\n" +
                        "2. Направлена из вершины Москва, длина 20",
                graph.getInArcsInStr("Минск")
        );
        Assertions.assertEquals("1. Направлена в вершину Минск, длина 10\n" +
                        "2. Направлена в вершину Москва, длина 5",
                graph.getOutArcsInStr("Ростов")
        );
        graph.clearGraph();
    }

    @Test
    public void changeArcSize() {
        createGraph();
        graph.changeArcSize("1", "2", 10, 20);
        graph.changeArcSize("3", "2", 20, 10);
        Assertions.assertEquals("1. Направлена в вершину 2, длина 20\n" +
                        "2. Направлена в вершину 3, длина 5",
                graph.getOutArcsInStr("1")
        );
        Assertions.assertEquals("1. Направлена в вершину 2, длина 10",
                graph.getOutArcsInStr("3")
        );
        graph.clearGraph();
    }

    @Test
    public void delVertex() {
        createGraph();
        graph.delVertex("2");
        Assertions.assertEquals("1. Направлена в вершину 3, длина 5",
                graph.getOutArcsInStr("1")
        );
        Assertions.assertEquals("1. Направлена из вершины 1, длина 5",
                graph.getInArcsInStr("3")
        );
        Assertions.assertThrows(NullPointerException.class, () -> {
            graph.getInArcsInStr("2");
        });
        graph.clearGraph();
    }

    @Test
    public void delArc() {
        createGraph();
        graph.delArc("1", "2", 10);
        Assertions.assertEquals("1. Направлена в вершину 3, длина 5",
                graph.getOutArcsInStr("1")
        );
        Assertions.assertEquals("1. Направлена из вершины 3, длина 20",
                graph.getInArcsInStr("2")
        );
        graph.clearGraph();
    }
}
