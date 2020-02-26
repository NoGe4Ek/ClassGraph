package Implementation;

import GraphClass.Graph;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        Scanner sc = new Scanner(System.in);
        boolean open = true;
        System.out.println("~~ Список команд ~~ \n addv - добавить вершину, adda - добавить дугу," +
                "\n delv - удалить вершину, dela - удалить дугу,\n chn - сменить имя вершины, " +
                "chs - сменить размер дуги, \n out - посмотреть список исходящих дуг, " +
                "in - посмотреть список входящих дуг");

        while (open) {
            String str = sc.nextLine();
            if (str.matches("addv [\\wа-яё]+")){
                graph.addVertex(str.split(" ")[1]);
            }
            if (str.matches("addv [\\wа-яё]+ [\\wа-яё]+ \\d+ [+-]")){
                if (str.split(" ")[4].equals("+"))
                    graph.addVertex(str.split(" ")[1], str.split(" ")[2],
                            Integer.parseInt(str.split(" ")[3]), true);
                if (str.split(" ")[4].equals("-"))
                    graph.addVertex(str.split(" ")[1], str.split(" ")[2],
                            Integer.parseInt(str.split(" ")[3]), false);
            }
            if (str.matches("adda [\\wа-яё]+ [\\wа-яё]+ \\d+")){
                graph.addArc(str.split(" ")[1], str.split(" ")[2],
                        Integer.parseInt(str.split(" ")[3]));
            }
            if (str.matches("out [\\wа-яё]+")){
                if (graph.getOutArcs(str.split(" ")[1]) != null)
                    System.out.println(graph.getOutArcs(str.split(" ")[1]));
            }
            if (str.matches("in [\\wа-яё]+")){
                if (graph.getInArcs(str.split(" ")[1]) != null)
                    System.out.println(graph.getInArcs(str.split(" ")[1]));
            }
            if (str.matches("chn [\\wа-яё]+ [\\wа-яё]+")){
                graph.changeName(str.split(" ")[1], str.split(" ")[2]);
            }
            if (str.matches("chs [\\wа-яё]+ [\\wа-яё]+ \\d+")){
                graph.changeArcSize(str.split(" ")[1], str.split(" ")[2],
                        Integer.parseInt(str.split(" ")[3]));
            }
            if (str.matches("dela [\\wа-яё]+ [\\wа-яё]+")){
                graph.delArc(str.split(" ")[1], str.split(" ")[2]);
            }
            if (str.matches("delv [\\wа-яё]+")){
                graph.delVertex(str.split(" ")[1]);
            }
            if (str.matches("close")){
                open = false;
            }
        }
    }
}
