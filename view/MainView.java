package view;

import java.util.*;

import classes.*;

public class MainView {

    public void ShowTableToys(List<Toy> toys) {
        //Вывести в консоль таблицу игрушек
        System.out.println("\nТаблица игрушек\n----------------");
        for (Toy item : toys) {
            System.out.println(item.toString());
            System.out.println("********************");
        }
    }

}
