package GraphClass;

import java.util.*;
import java.util.logging.Logger;

/** Класс ориентированного графа */
public class Graph {
    /** Поле структуры графа */
    private Map<String, Map<String, Integer>> graph = new HashMap<>();

    /** Поле начальной вершины */
    private String startVertex = "";

    private static Logger log = Logger.getLogger(Graph.class.getName());

    /** Функция очистки графа */
    public void clearGraph (){
        startVertex = "";
        graph.clear();
    }

    /**
     * Функция добавления начальной вершины в граф
     * @param name - имя создаваемой вершины
     * @return возвращает результат выполнения в формате true/false
     *
     * Для добавления последующих вершин смотрите перегрузку
     * @see Graph#addVertex(String, String, int, boolean)
     */
    public boolean addVertex(String name){
        if (startVertex.equals("")) {
            startVertex = name;
            graph.put(name, new HashMap<>());
            return true;
        } else {
            log.info("Начальная вершина уже существует");
            return false;
        }
    }

    /**
     * Функция добавления НЕ начальной вершины в граф
     * @param name - имя создаваемой вершины
     * @param frto - имя вершины, с коротой хотим связать
     * @param size - размер дуги между вершинами
     * @param dur - направление дуги (true - от новой вершины к старой, false - реверсивно)
     * @return возвращает результат выполнения в формате true/false
     */
    public boolean addVertex(String name, String frto, int size, boolean dur){
        if (graph.get(name) == null) {
            if (startVertex.equals("")) {
                log.info("Сначала создайте начальную вершину");
                return false;
            } else {
                if (dur) {
                    graph.put(name, new HashMap<>());
                    return addArc(name, frto, size);
                } else {
                    graph.put(name, new HashMap<>());
                    return addArc(frto, name, size);
                }
            }
        } else {
            log.info("Вершина уже существует");
          return false;
        }
    }

    /**
     * Функция добавления дуги между существующими вершинами
     * @param from - начальная вершина
     * @param to - конечная вершина
     * @param size - размер дуги между вершинами
     * @return возвращает результат выполнения в формате true/false
     */
    public boolean addArc(String from, String to, int size){
        if (graph.get(from) != null && graph.get(to) != null) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            if (graph.get(from) != null) {
                map = graph.get(from);
            }
            map.put(to, size);
            return true;
        } else {
            log.info("Вершина(-ны) не существуют");
            return false;
        }
    }

    /**
     * Функция удаления существующей вершины с проверкой на деление графа на несвязные части
     * @param name - имя удаляемой вершины
     * @return возвращает результат выполнения в формате true/false
     */
    public boolean delVertex(String name){
        if (graph.get(name) != null) {
            if (name.equals(startVertex))
                startVertex = "";
            graph.remove(name);

            for (int i = 0; i < graph.size(); i++) {
                graph.get(graph.keySet().toArray()[i]).remove(name);
                delSplited((String) graph.keySet().toArray()[i]);
            }
            return true;
        } else {
            log.info("Вершины не существует");
            return false;
        }
    }

    /**
     * Функция удаления существующей дуги с проверкой на деление графа на несвязные части
     * @param from - начальная вершина
     * @param to - конечная вершина
     * @return возвращает результат выполнения в формате true/false
     */
    public boolean delArc(String from, String to){
        if (graph.get(from) == null || graph.get(to) == null) {
            log.info("Дуги/вершина(-ы) не сущестсвует(-ют)");
            return false;
        }
        else {
            graph.get(from).remove(to);
            delSplited(from);
            delSplited(to);
            return true;
        }
    }

    /**
     * Функция смены имени существующей вершины
     * @param oldName - старое имя вершины
     * @param newName - новое имя вершины
     * @return возвращает результат выполнения в формате true/false
     */
    public boolean changeName(String oldName, String newName) {
        if (graph.get(newName) == null) {
            if (graph.get(oldName) != null) {
                graph.put(newName, graph.get(oldName));
                graph.remove(oldName);
                for (int i = 0; i < graph.size(); i++) {
                    if (graph.keySet().toArray()[i] != newName) {
                        if (graph.get(graph.keySet().toArray()[i]).get(oldName) != null) {
                            Map<String, Integer> map;
                            map = graph.get(graph.keySet().toArray()[i]);
                            map.put(newName, graph.get(graph.keySet().toArray()[i]).get(oldName));
                            map.remove(oldName);
                            graph.put((String) graph.keySet().toArray()[i], map);
                        }
                    }
                }
                return true;
            } else {
                log.info("Вершина не существует");
                return false;
            }
        } else {
            log.info("Вершина с таким именем уже существует");
            return false;
        }
    }

    /**
     * Функция изменения размера существующей дуги
     * @param from - начальная вершина
     * @param to - конечная вершина
     * @param newSize - новый размер для вершины
     * @return возвращает результат выполнения в формате true/false
     */
    public boolean changeArcSize(String from, String to, int newSize){
        if (graph.get(from) != null && graph.get(to) != null && graph.get(from).get(to) != null) {
            graph.get(from).put(to, newSize);
            return true;
        }
        else {
            log.info("Дуги/вершина(-ы) не сущестсвует(-ют)");
            return false;
        }
    }

    /**
     * Функция, возвращающая исходящие дуги из заданной вершины
     * @param from - вершина, у которой требуется узнать исходящие дуги
     * @return возвращает результат выполнения в формате Map [имя вершины, в которую приходит дуга - размер дуги]
     * @exception NullPointerException ошибка, возникающая при попытке найти исходящие дуги из несуществующей вершины
     */
    public Map<Object, Integer> getOutArcs(String from){
        if (graph.get(from) != null) {
            Map<Object, Integer> map = new HashMap<Object, Integer>();
            for (int i = 0; i < graph.get(from).size(); i++) {
                Integer arcSize = graph.get(from).get(graph.get(from).keySet().toArray()[i]);
                Object vertexIn = graph.get(from).keySet().toArray()[i];
                map.put(vertexIn, arcSize);
            }

            if (map.keySet().size() == 0) {
                log.info("У вершины нет исходящих дуг");
            }
            return map;
        } else {
            log.info("Вершины не существует");
            return new HashMap<>();
        }
    }

    /**
     * Функция, возвращающая входящие дуги в заданную вершину
     * @param to - вершина, у которой требуется узнать входящие дуги
     * @return возвращает результат выполнения в формате Map [имя вершины, из которой приходит - размер дуги]
     * @exception NullPointerException ошибка, возникающая при попытке найти входящие дуги в несуществующую вершину
     */
    public Map<Object, Integer> getInArcs(String to) {
        if (graph.get(to) != null) {
            Map<Object, Integer> map = new HashMap<Object, Integer>();
            for (int i = 0; i < graph.size(); i++) {
                if (!graph.keySet().toArray()[i].equals(to))
                    for (int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++) {
                        if (graph.get(graph.keySet().toArray()[i]).keySet().toArray()[j].equals(to)) {
                            Integer arcSize = graph.get(graph.keySet().toArray()[i]).
                                    get(graph.get(graph.keySet().toArray()[i]).keySet().toArray()[j]);
                            Object vertexOut = graph.keySet().toArray()[i];
                            map.put(vertexOut, arcSize);
                        }
                    }
            }
            if (map.keySet().size() == 0) {
                log.info("У вершины нет входящих дуг");
            }
            return map;
        } else {
            log.info("Вершины не существует");
            return new HashMap<>();
        }
    }

    /**
     * Функция, проверки графа на деление и удаление побочной част(и/-ей)
     * Побочной частью считается та часть, которая не связана с начальной вершиной {@link Graph#startVertex}
     * @param checkable - вершина, связь которой необходимо проверить (подозрительная на разрыв)
     * @return возвращает результат выполнения в формате true/false
     */
    private boolean delSplited(String checkable){
        if (checkable == startVertex)
            return false;
        Map<String, Integer> peakSt = new HashMap();
        for (int i = 0; i < graph.size(); i++){
            peakSt.put((String) graph.keySet().toArray()[i], 1);
        }
        peakSt.put(checkable, 2);
        Deque<String> deque = new LinkedList<String>();
        deque.addFirst(checkable);

        while ((peakSt.containsValue(1) || peakSt.containsValue(2)) && deque.size() != 0) {
            String current = deque.getFirst();
            if(peakSt.get(current) == 2) {
                for (int i = 0; i < graph.get(current).size(); i++) {
                    if (graph.get(current).keySet().toArray()[i].equals(startVertex))
                        return false;
                    if (peakSt.get(graph.get(current).keySet().toArray()[i]) == 1) {
                        deque.addLast((String) graph.get(current).keySet().toArray()[i]);
                        peakSt.put((String) graph.get(current).keySet().toArray()[i], 2);
                    }
                }
                for (int i = 0; i < graph.size(); i++) {
                    for (int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++) {
                        if (graph.get(graph.keySet().toArray()[i]).keySet().toArray()[j].equals(current)) {
                            if (graph.keySet().toArray()[i].equals(startVertex))
                                return false;
                            if (peakSt.get(graph.keySet().toArray()[i]) == 1) {
                                deque.addLast((String) graph.keySet().toArray()[i]);
                                peakSt.put((String) graph.keySet().toArray()[i], 2);
                            }
                        }
                    }
                }
                peakSt.put(current, 3);
                deque.removeFirst();
            }
        }

        for (int i = 0; i < peakSt.size(); i++){
            if (peakSt.get(peakSt.keySet().toArray()[i]) == 3){
                delVertexAfterSplit((String) peakSt.keySet().toArray()[i]);
            }
        }

        return true;
    }

    /**
     * Функция, для безопасного удаления вершин после выполнения проверки {@link Graph#delSplited(String)}
     * @param name - имя удаляемой вершины
     * @return возвращает результат выполнения в формате true/false
     */
    private boolean delVertexAfterSplit(String name){
        if (graph.get(name) != null) {
            if (name.equals(startVertex))
                startVertex = "";
            graph.remove(name);

            for (int i = 0; i < graph.size(); i++) {
                graph.get(graph.keySet().toArray()[i]).remove(name);
            }
            return true;
        } else {
            log.info("Вершины не существует");
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph1 = (Graph) o;
        return Objects.equals(graph, graph1.graph) &&
                Objects.equals(startVertex, graph1.startVertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, startVertex);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "graph=" + graph +
                ", startVertex='" + startVertex + '\'' +
                '}';
    }
}
