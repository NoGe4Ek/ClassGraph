package GraphClass;


import Implementation.Pair;

import java.util.*;

public class Graph {

    public static class Arc {
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

    private Map<String, Pair<List<Arc>, List<Pair<String, Integer>>>> graph = new HashMap();

    public void clearGraph (){
        graph.clear();
    }

    public void addVertex(String name){
        graph.put(name, new Pair(null, null));
    }

    public void addArc(String from, String to, int size){
        List<Arc> toList;
        List<Pair<String,Integer>> fromList;
        if (null == graph.get(from).fst)
            toList = new ArrayList();
        else
            toList = graph.get(from).fst;
        if (null == graph.get(to).snd){
            fromList = new ArrayList();
        }
        else {
            fromList = graph.get(to).snd;
        }
        toList.add(new Arc(to, size));
        fromList.add(new Pair(from, toList.size() - 1));
        graph.put(from, new Pair(toList, graph.get(from).snd));
        graph.put(to, new Pair(graph.get(to).fst, fromList));
    }

    public void delVertex(String name){
        for(int i = 0; i < graph.get(name).snd.size(); i++) {
            graph.get(graph.get(name).snd.get(i).fst).fst.remove((int)graph.get(name).snd.get(i).snd);
        }
        graph.remove(name);
    }
    public void delArc(String from, String to, int size){
        for (int i = 0; i < graph.get(from).fst.size(); i++){
            if (graph.get(from).fst.get(i).getName().equals(to) && graph.get(from).fst.get(i).getSize() == size){
                graph.get(from).fst.remove(i);
            }
        }
    }
    public void changeName(String oldName, String newName) {
        if (graph.get(oldName).fst != null)
            for (int i = 0; i < graph.get(oldName).fst.size(); i++){
                for (int j = 0; j < graph.get(graph.get(oldName).fst.get(i).name).snd.size(); j++){
                    if (graph.get(graph.get(oldName).fst.get(i).name).snd.get(j).fst.equals(oldName)) {
                        graph.get(graph.get(oldName).fst.get(i).name).snd.get(j).fst = newName;
                        //graph.get(graph.get(oldName).fst.get(i).name).snd.remove(j);
                        //graph.get(graph.get(oldName).fst.get(i).name).snd.add(new Pair(newName, graph.get(graph.get(oldName).fst.get(i).name).snd.get(j).snd));
                    }
                }
            }
        if (graph.get(oldName).snd != null)
            for (int i = 0; i < graph.get(oldName).snd.size(); i++) {
                    graph.get(graph.get(oldName).snd.get(i).fst).fst.
                            get((int)graph.get(oldName).snd.get(i).snd).setName(newName);
            }
        graph.put(newName, graph.get(oldName));
        graph.remove(oldName);
    }
    public void changeArcSize(String from, String to, int oldSize, int newSize){
        for (int i = 0; i < graph.get(from).fst.size(); i++){
            if (graph.get(from).fst.get(i).getName().equals(to) && graph.get(from).fst.get(i).getSize() == oldSize){
                graph.get(from).fst.get(i).setSize(newSize);
            }
        }
    }
    public List<Arc> getOutArcs(String from){
        List<Arc> list = new ArrayList();
        if (graph.get(from).fst != null)
        for (int i = 0; i < graph.get(from).fst.size(); i++){
            list.add(new Arc(graph.get(from).fst.get(i).getName(), graph.get(from).fst.get(i).getSize()));
        }
        return list;
    }
    public List<Arc> getInArcs(String to) {
        List<Arc> list = new ArrayList();
        if (graph.get(to).snd != null)
            for (int i = 0; i < graph.get(to).snd.size(); i++) {
                list.add(new Arc(graph.get(to).snd.get(i).fst,
                        graph.get(graph.get(to).snd.get(i).fst).fst.get(graph.get(to).snd.get(i).snd).size));
            }
        return list;
    }

    public String getOutArcsInStr(String from){
        String s = "";
        for (int i = 0; i < graph.get(from).fst.size(); i++){
            if (!s.equals("")){
                s += System.lineSeparator();
            }
            s += i+1 + ". Направлена в вершину " + graph.get(from).fst.get(i).getName() +
                    ", длина " + graph.get(from).fst.get(i).getSize();
        }
        return s;
    }
    public String getInArcsInStr(String to) {
        String s = "";
        int ch = 0;
        if (graph.get(to).snd != null)
            for (int i = 0; i < graph.get(to).snd.size(); i++) {
                if (!s.equals("")) {
                    s += System.lineSeparator();
                }
                s += ch + 1 + ". Направлена из вершины " + graph.get(to).snd.get(i).fst +
                        ", длина " + graph.get(graph.get(to).snd.get(i).fst).fst.get(graph.get(to).snd.get(i).snd).size;
                ch++;
            }
        if (s.equals(""))
            throw new NullPointerException();
        else
            return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph1 = (Graph) o;
        return Objects.equals(graph, graph1.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph);
    }
}
