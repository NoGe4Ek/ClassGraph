import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    public class Arc {
        public Arc(String name, int size){
            this.name = name;
            this.size = size;
        }

        private String name;
        private int size;

        public void setName(String name){
            this.name = name;
        }
        public void setSize(int size){
            this.size = size;
        }
        public String getName(){
            return name;
        }
        public int getSize(){
            return  size;
        }
    }

    private HashMap<String, ArrayList<Arc>> graph = new HashMap();

    void clearGraph (){
        graph.clear();
    }

    void addVertex(String name){
        graph.put(name, null);
    }
    void addArc(String from, String to, int size){
        ArrayList<Arc> list;
        if (!new Check(graph.get(from)).isValid())
            list = new ArrayList();
        else
            list = graph.get(from);
        list.add(new Arc(to, size));
        graph.put(from, list);
    }
    void delVertex(String name){
        graph.remove(name);
        for(int i = 0; i < graph.size(); i++){
            for(int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++){
                if (graph.get(graph.keySet().toArray()[i]).get(j).getName().equals(name)){
                    graph.get(graph.keySet().toArray()[i]).remove(j);
                }
            }
        }
    }
    void delArc(String from, String to, int size){
        for (int i = 0; i < graph.get(from).size(); i++){
            if (graph.get(from).get(i).getName().equals(to) && graph.get(from).get(i).getSize() == size){
                graph.get(from).remove(i);
            }
        }
    }
    void changeName(String oldName, String newName){
        graph.put(newName, graph.get(oldName));
        graph.remove(oldName);
        for(int i = 0; i < graph.size(); i++){
            if (new Check(graph.get(graph.keySet().toArray()[i])).isValid()) {
                for (int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++) {
                    if (graph.get(graph.keySet().toArray()[i]).get(j).getName().equals(oldName)) {
                        graph.get(graph.keySet().toArray()[i]).get(j).setName(newName);
                    }
                }
            }
        }
    }
    void changeArcSize(String from, String to, int oldSize, int newSize){
        for (int i = 0; i < graph.get(from).size(); i++){
            if (graph.get(from).get(i).getName().equals(to) && graph.get(from).get(i).getSize() == oldSize){
                graph.get(from).get(i).setSize(newSize);
            }
        }
    }
    String getOutArcs(String from){
        String s = "";
        for (int i = 0; i < graph.get(from).size(); i++){
            if (!s.equals("")){
                s += "\n";
            }
            s += i+1 + ". Направлена в вершину " + graph.get(from).get(i).getName() +
                    ", длина " + graph.get(from).get(i).getSize();
        }
        return s;
    }
    String getInArcs(String to){
        String s = "";
        int ch = 0;
        for(int i = 0; i < graph.size(); i++){
            if (new Check(graph.get(graph.keySet().toArray()[i])).isValid()) {
                for (int j = 0; j < graph.get(graph.keySet().toArray()[i]).size(); j++) {
                    if (graph.get(graph.keySet().toArray()[i]).get(j).getName().equals(to)) {
                        if (!s.equals("")){
                            s += "\n";
                        }
                        s += ch + 1 + ". Направлена из вершины " + graph.keySet().toArray()[i] +
                                ", длина " + graph.get(graph.keySet().toArray()[i]).get(j).getSize();
                        ch++;
                    }
                }
            }
        }
        if (s.equals(""))
            throw new NullPointerException();
        else
            return s;
    }
}
