package view;

import java.util.*;

import classes.*;

public class ToysView {
    List<Toy> toys;
    
    public ToysView(List<Toy> toys) {
        this.toys = toys;
    }

    public void ShowTable() {
        //Вывести в консоль таблицу игрушек
        System.out.println("Таблица игрушек\n---------------");
        for (Toy item : toys) {
            System.out.println(item.toString());
            System.out.println("********************");
        }
    }

}
