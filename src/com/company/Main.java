package com.company;

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
            if (str.matches("addv \\w+")){
                graph.addVertex(str.split(" ")[1]);
            }
            if (str.matches("adda \\w+ \\w+ \\d+")){
                graph.addArc(str.split(" ")[1], str.split(" ")[2],
                        Integer.parseInt(str.split(" ")[3]));
            }
            if (str.matches("out \\w+")){
                graph.getOutArcs(str.split(" ")[1]);
            }
            if (str.matches("in \\w+")){
                graph.getInArcs(str.split(" ")[1]);
            }
            if (str.matches("chn \\w+ \\w+")){
                graph.changeName(str.split(" ")[1], str.split(" ")[2]);
            }
            if (str.matches("chs \\w+ \\w+ \\d+ \\d+")){
                graph.changeArcSize(str.split(" ")[1], str.split(" ")[2],
                        Integer.parseInt(str.split(" ")[3]), Integer.parseInt(str.split(" ")[4]));
            }
            if (str.matches("dela \\w+ \\w+ \\d+")){
                graph.delArc(str.split(" ")[1], str.split(" ")[2],
                        Integer.parseInt(str.split(" ")[3]));
            }
            if (str.matches("delv \\w+")){
                graph.delVertex(str.split(" ")[1]);
            }
            if (str.matches("close")){
                open = false;
            }
        }
    }
}
