/*
Создайте класс Presenter со следующими функциями:
• Распечатать всех студентов и посещаемость каждого в процентах;
• Распечатать студентов, отсортировав их по убыванию посещаемости
(вверху самые посещающие);
• Распечатать студентов с посещаемостью ниже 25%.
*/

import java.util.*;

import view.*;

public class Presenter {
    Menu menu;

    public Presenter() {    
        //инициализация объекта меню
        menu = new Menu();
    }

    public void run() {
        //Построение и работа с меню
        Scanner sc = new Scanner(System.in);
        while (true) {
            //формируем новую позицию меню = прежнее меню + выбранный пункт
            if (menu.getChoice() != "") {
                if (menu.getPrevPos() != "") {
                    menu.setNewPos((menu.getPrevPos() + "," +
                                    menu.getChoice()).trim());
                } else {
                    menu.setNewPos(menu.getChoice().trim());
                }
            }
            //Обработка в соотвествии с новой позицией меню
            switch(menu.getNewPos()) {
                case(""): //главное меню
                    menu.showMainMenu();
                    menu.setPrevPos(menu.getNewPos());
                    break;
                case("3"): //показать меню Игрушки
                    menu.showToysMenu();
                    menu.setPrevPos(menu.getNewPos());
                    break;
                case("3,1"): //меню 3.Игрушки - Показать таблицу
                    menu.showTableToysAll();
                    menu.setPrevPos(menu.getNewPos());
                    break;

                case("0"):  //означает - Выход из программы
                    menu.showProgramExit();
                    return;
                default:
                    menu.showMenuItemNotFound();
                    //возвращаем указатель меню на предыдущее положение
                    menu.setNewPos(menu.getPrevPos());
                    break;
            }
            //System.out.println(menu.getNewPos()); //для отладки
            
            // Считываем новый выбранный пункт меню
            System.out.printf("Укажите пункт меню: ");
            try {
                menu.setChoice(sc.next());
            } catch (NoSuchElementException exception) {
                System.out.println("Пункт меню не выбран");
                menu.setChoice("");
            }
        }
    }



/* 
            switch (ch) {
                case 1:
                    showAllAgents();
                    break;
                case 2:
                    showAllStudentsSortDescByAttendance();
                    break;
                case 3:
                    showAStudentsAttendanceLess(25);
                    break;
            }
            if (cur_menu_pos == "0") {
                System.out.println("Программа завершена.");
                sc.close();
                break;
            }*/

    // Распечатать всех студентов и посещаемость каждого в процентах
    /* 
    private void showAllAgents() {
        view.ShowResult(model.getAgentsAll());
    }

    
    // Распечатать студентов, отсортировав их по убыванию посещаемости
    // (вверху самые посещающие);
    
    private void showAllStudentsSortDescByAttendance() {
        view.ShowResult(model.getStudentsAllSortedDescByAttendance());
    }

    // Распечатать студентов с посещаемостью ниже чем border%.
    private void showAStudentsAttendanceLess(int border) {
        view.ShowResult(model.getStudentsAttendanceLess(border));
    }*/
}
