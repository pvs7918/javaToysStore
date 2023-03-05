package view;

import view.*;
import model.*;

public class Menu {
    private String prevPos; //текущая позиция меню в формате "1,2" пустая строка означает выбрано главное меню
    private String choice; //текущий выбранный пункт меню
    private String newPos;     // = prevPos + choice

    

    public String getPrevPos() {
        return prevPos;
    }

    public String getChoice() {
        return choice;
    }

    public String getNewPos() {
        return newPos;
    }

    public void setPrevPos(String prevPos) {
        this.prevPos = prevPos;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public void setNewPos(String newPos) {
        this.newPos = newPos;
    }

    public Menu() {
        prevPos = "";
        choice = "";
        newPos = "";
    }

    public void showProgramGreeting() {
        //показать заголовок программы
        System.out.println();
        String s1 = "Программа - Проведение розыгрыша призов магазина игрушек";
        System.out.println(s1);
        System.out.println("-".repeat(s1.length()));
    }

    public void showProgramExit() {
        //показать заголовок программы
        System.out.println();
        System.out.println("Завершение работы программы");
    }

    public void showMenuItemNotFound() {
        System.out.println("Не найден обработчик для указанного пункта меню.");
    }
    
    public void showMainMenu() {
        showProgramGreeting();
        // показать меню Игрушки в консоли
        String s1 = "Главное меню";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()) + "\nВыберите пункт меню:");
        System.out.println("1. Розыгрыши призов.");
        System.out.println("2. Участники розыгрыша.");
        System.out.println("3. Игрушки.");
        System.out.println("4. Призы, которые нужно вручить.");
        System.out.println("5. Призы врученные.");
        System.out.println("0. Выход из программы.");
    }

    public void showToysMenu() {
        // показать меню Игрушки в консоли
        System.out.println();
        System.out.println("\nТаблица Игрушки\n----------------\nВыберите пункт меню:");
        System.out.println("1. Показать таблицу.");
        System.out.println("2. Найти игрушку по id.");
        System.out.println("3. Добавить игрушку.");
        System.out.println("4. Редактировать игрушку.");
        System.out.println("5. Изменить вес игрушки.");
        System.out.println("6. Удалить игрушку.");        
        System.out.println("0. Выход.");
    }

    public void showTableToysAll() {
        // Создаем объект Модель - она содержит коллекцию объектов нужного типа
        // позволяет загружать, сохранять и вносить изменения в коллекцию
        System.out.println();
        //показать таблицу Игрушки полностью
        ToysModel toysModel = new ToysModel("./db/", "toys.csv");
        //загружаем данные в модель из файла
        toysModel.load();

        ToysView toysView = new ToysView(toysModel.getToysAll());
        toysView.ShowTable();
    }
}
