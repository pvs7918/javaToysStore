import view.*;
import classes.*;
import model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu {
    private String prevPos; // текущая позиция меню в формате "1,2" пустая строка означает выбрано главное
                            // меню
    private String choice; // текущий выбранный пункт меню
    private String newPos; // = prevPos + choice
    private boolean ShowNewChoice; // = признак необходимости показать ввод нового пункта меню
    private Scanner sc;
    DateTimeFormatter formatter; //формат даты времени

    public void run() {
        showProgramGreeting();

        // Построение и работа с меню
        sc = new Scanner(System.in);
        while (true) {
            ShowNewChoice = true;
            // формируем новую позицию меню = прежнее позиция меню + выбранный пункт
            if (getChoice() != "") {
                if (getPrevPos() != "") {
                    setNewPos((getPrevPos() + "," +
                            getChoice()));
                } else {
                    setNewPos(getChoice());
                }
            } else {
                // откатываемся на предыдущую позицию меню
                if (getPrevPos() != "") {
                    setNewPos(getPrevPos());
                } else {
                    setNewPos("");
                }
            }
            // обработка общих случаев
            if (getChoice().equals("0")) {
                // означает - Перейти в главное меню
                showMainMenu();
            } else if (getChoice().equals("q")) {
                // означает - Выход из программы
                showProgramExit();
                return;
            } else {
                // Обработка конкретных пунктов меню в соответствии с новой позицией меню
                switch (getNewPos()) {
                    case (""): // главное меню
                        showMainMenu();
                        break;

                    // меню Покупатели
                    case ("1"): // показать меню Покупатели
                        showBuyersMenu();
                        break;

                    case ("1,1"): // меню Покупатели - Показать таблицу
                        BuyersShowTableAll();
                        break;

                    case ("1,2"): // меню Покупатели - Добавить
                        if (BuyerAddNew()) {
                            BuyersShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("1,3"): // меню Покупатели - Редактировать
                        if (BuyerEdit()) {
                            BuyersShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("1,4"): // меню Покупатели - Удалить
                        if (BuyerDeleteById()) {
                            BuyersShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    // меню Игрушки
                    case ("2"): // показать меню Игрушки
                        showToysMenu();
                        break;

                    case ("2,1"): // меню Игрушки - Показать таблицу
                        ToysShowTableAll();
                        break;

                    case ("2,2"): // меню Игрушки - Добавить игрушку
                        if (ToyAddNew() == true) {
                            ToysShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("2,3"): // меню Игрушки - Редактировать игрушку
                        if (ToyEdit()) {
                            ToysShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("2,4"): // меню Игрушки - Удалить игрушку
                        if (ToyDeleteById()) {
                            ToysShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    // меню Розыгрыш призов
                    case ("3"): // показать меню Розыгрыш призов
                        showDrawingMenu();
                        break;

                    case ("3,1"): // меню Розыгрыш призов - Показать разыгранные призы
                        PrizesToAwardShowAll();
                        showDrawingMenu();
                        break;

                    case ("3,2"): // меню Розыгрыш призов - Разыграть следующий приз
                        if (PrizeAddNew()) {
                            PrizesToAwardShowAll();
                            showDrawingMenu();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("3,3"): // меню Розыгрыш призов - Отметить приз как врученный
                        if (PrizeSetAsAwarded()) {
                            PrizesAwardedShowAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("3,4"): // меню Розыгрыш призов - Показать врученные призы
                        PrizesAwardedShowAll();
                        break;

                    default:
                        showMenuItemNotFound();
                        // возвращаем указатель меню на предыдущее положение
                        // потому что обработчик не найден
                        setChoice("");
                        ShowNewChoice = false;
                        break;
                }
            }
            // для отладки
            // System.out.println("Отладка. Текущая позиция меню: " + getNewPos());

            if (ShowNewChoice) {
                // Считываем новый выбранный пункт меню - Choice
                System.out.printf("Укажите пункт меню: ");
                try {
                    setChoice(sc.nextLine().trim());
                } catch (NoSuchElementException exception) {
                    System.out.println("Пункт меню не выбран");
                    setChoice("");
                }
            }
        }
    }

    public void showProgramGreeting() {
        // показать заголовок программы
        System.out.println();
        String s1 = "Программа - Проведение розыгрыша призов магазина игрушек";
        System.out.println(s1);
        System.out.println("-".repeat(s1.length()));
    }

    public void showMainMenu() {
        // показать меню Игрушки в консоли
        String s1 = "Главное меню";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        System.out.println("1. Покупатели (участники розыгрыша).");
        System.out.println("2. Игрушки.");
        System.out.println("3. Розыгрыш призов.");
        System.out.println("q. Выход из программы.");
        ResetMenuPos();
    }

    public void showBuyersMenu() {
        // показать меню Покупатели в консоли
        String s1 = "Меню-Таблица Покупатели (Участники розыгрыша)";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        System.out.println("1. Показать таблицу.");
        System.out.println("2. Добавить покупателя.");
        System.out.println("3. Редактировать покупателя.");
        System.out.println("4. Удалить покупателя.");
        System.out.println("0. Назад в Главное меню.");
        System.out.println("q. Выход.");
        setPrevPos(getNewPos());
    }

    public void showToysMenu() {
        // показать меню Игрушки в консоли
        String s1 = "Меню-Таблица Игрушки";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        System.out.println("1. Показать таблицу.");
        System.out.println("2. Добавить игрушку.");
        System.out.println("3. Редактировать игрушку.");
        System.out.println("4. Удалить игрушку.");
        System.out.println("0. Назад в Главное меню.");
        System.out.println("q. Выход.");
        setPrevPos(getNewPos());
    }

    public void showDrawingMenu() {
        // показать меню Розыгрыш призов в консоли
        System.out.println("\nМеню-Розыгрыш призов\n----------------");
        System.out.println("1. Показать таблицу-Разыгранные призы.");
        System.out.println("2. Разыграть следующий приз.");
        System.out.println("3. Отметить приз как врученный.");
        System.out.println("4. Показать таблицу-Врученные призы.");
        System.out.println("0. Назад в Главное меню.");
        System.out.println("q. Выход.");
        setPrevPos(getNewPos());
    }

    public void showProgramExit() {
        // показать заголовок программы
        System.out.println();
        System.out.println("Завершение работы программы");
    }

    public void showMenuItemNotFound() {
        System.out.println("Не найден обработчик для указанного пункта меню.");
    }

    // методы для обработки меню - Покупатели

    public void BuyersShowTableAll() {
        // Создаем объект Модель - она содержит коллекцию объектов нужного типа
        // позволяет загружать, сохранять и вносить изменения в коллекцию
        // показать таблицу Игрушки полностью
        // загружаем данные в модель из файла
        BuyersModel buyersModel = new BuyersModel();
        if (buyersModel.load()) {
            BuyersView buyersView = new BuyersView(buyersModel.getBuyersAll());
            buyersView.ShowTable();
        }
        ReturnToPrevPos();
        showBuyersMenu();
    }

    public boolean BuyerAddNew() {
        // Добавление игрушки
        // через параметр передаем сканер чтобы не создавать новый.
        // И для консольного приложения рекомендуется использовать один сканер
        // загружаем данные в модель из файла
        BuyersModel buyersModel = new BuyersModel();
        if (!buyersModel.load()) {
            System.out.println("\nФункция добавления покупателя прервана.");
            return false;
        }

        int curId = buyersModel.getNewId();
        // Вводим значения полей
        System.out.println("\nДобавление покупателя. Введите значения полей.");
        System.out.print("ФИО: ");
        try {
            String curFullName = sc.nextLine();
            System.out.print("Номер чека: ");
            String curCheckNumber = sc.nextLine();
            System.out.print("Номер телефона: ");
            String curPhone = sc.nextLine();
            Buyer curBuyer = new Buyer(curId, curFullName, curCheckNumber,
                    curPhone);
            buyersModel.add(curBuyer);
        } catch (Exception ex) {
            System.out.println("Ошибка при вводе данных о покупателе.\n" + ex.toString());
            return false;
        }

        if (buyersModel.save()) {
            System.out.println("Новый покупатель успешно добавлен!");
        } else {
            System.out.println("Ошибка при добавлении нового покупателя.");
            return false;
        }
        return true;
    }

    public boolean BuyerDeleteById() {
        // Удаление игрушки
        // загружаем данные в модель из файла

        // Показываем список игрушек для выбора id для удаления
        BuyersModel buyersModel = new BuyersModel();
        if (buyersModel.load()) {
            BuyersView buyersView = new BuyersView(buyersModel.getBuyersAll());
            buyersView.ShowTable();
        }
        System.out.print("\nВведите id удаляемого покупателя: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            // удаляем запись
            if (buyersModel.deleteById(curId)) {
                // сохраняем данные в файл
                buyersModel.save();
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Ошибка при удалении покупателя.\n" + ex.toString());
            return false;
        }
        return false;
    }

    public boolean BuyerEdit() {
        // Редактирование игрушки
        Buyer editedBuyer;
        // Показываем список игрушек для выбора id для редактирования
        BuyersModel buyersModel = new BuyersModel();
        if (buyersModel.load()) {
            BuyersView buyersView = new BuyersView(buyersModel.getBuyersAll());
            buyersView.ShowTable();
        }
        System.out.print("\nВведите id редактируемого покупателя: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            editedBuyer = buyersModel.getBuyerById(curId);
            String curValue;
            System.out.println("Введите новые значения полей (Enter - оставить прежнее значение).");
            // Получаем новые значения полей
            System.out.print("ФИО (прежнее значение): " +
                    editedBuyer.getFullName() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedBuyer.setFullName(curValue);

            System.out.print("Номер чека (прежнее значение): " +
                    editedBuyer.getCheckNumber() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedBuyer.setCheckNumber(curValue);

            System.out.print("Номер телефона (прежнее значение): " +
                    editedBuyer.getPhone() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedBuyer.setPhone(curValue);

            // сохраняем данные в файл
            if (buyersModel.save()) {
                System.out.println("Данные покупателя с id=" + curId + " успешно отредактированы.");
                return true;
            }

        } catch (Exception ex) {
            System.out.println("Ошибка при редактировании данных покупателя.\n" + ex.toString());
            return false;
        }
        return false;
    }

    // методы для обработки меню - Игрушки
    public void ToysShowTableAll() {
        // Создаем объект Модель - она содержит коллекцию объектов нужного типа
        // позволяет загружать, сохранять и вносить изменения в коллекцию
        // показать таблицу Игрушки полностью
        // загружаем данные в модель из файла
        ToysModel toysModel = new ToysModel();
        if (toysModel.load()) {
            ToysView toysView = new ToysView(toysModel.getToysAll());
            toysView.ShowTable();
        }

        ReturnToPrevPos(); // курсор меню возвращаем на предыдущее положение - "3"
        showToysMenu();
    }

    public boolean ToyAddNew() {
        // Добавление игрушки
        // через параметр передаем сканер чтобы не создавать новый.
        // И для консольного приложения рекомендуется использовать один сканер
        // загружаем данные в модель из файла
        ToysModel toysModel = new ToysModel();
        if (!toysModel.load()) {
            System.out.println("\nФункция добавления игрушки прервана.");
            return false;
        }

        int curId = toysModel.getNewId();
        // Вводим значения полей
        System.out.println("\nДобавление игрушки. Введите значения полей.");
        System.out.print("Название: ");
        try {
            String curName = sc.nextLine();
            System.out.print("Количество: ");
            int curCount = Integer.parseInt(sc.nextLine());
            System.out.print("Цена: ");
            float curPrice = Float.parseFloat(sc.nextLine());
            System.out.print("Вес: ");
            int curWeight = Integer.parseInt(sc.nextLine());
            Toy curToy = new Toy(curId, curName, curCount,
                    curPrice, curWeight);
            toysModel.add(curToy);
        } catch (Exception ex) {
            System.out.println("Ошибка при вводе данных.\n" + ex.toString());
            return false;
        }

        if (toysModel.save()) {
            System.out.println("Новая игрушка успешно добавлена!");
        } else {
            System.out.println("Ошибка при добавлении новой игрушки.");
            return false;
        }
        return true;
    }

    public boolean ToyDeleteById() {
        // Удаление игрушки
        // загружаем данные в модель из файла

        // Показываем список игрушек для выбора id для удаления
        ToysModel toysModel = new ToysModel();
        if (toysModel.load()) {
            ToysView toysView = new ToysView(toysModel.getToysAll());
            toysView.ShowTable();
        }
        System.out.print("\nВведите id удаляемой игрушки: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            // удаляем запись
            if (toysModel.deleteById(curId)) {
                // сохраняем данные в файл
                toysModel.save();
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Ошибка при удалении игрушки.\n" + ex.toString());
            return false;
        }
        return false;
    }

    public boolean ToyEdit() {
        // Редактирование игрушки
        Toy editedToy;
        // Показываем список игрушек для выбора id для редактирования
        ToysModel toysModel = new ToysModel();
        if (toysModel.load()) {
            ToysView toysView = new ToysView(toysModel.getToysAll());
            toysView.ShowTable();
        }
        System.out.print("\nВведите id редактируемой игрушки: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            editedToy = toysModel.getToyById(curId);
            String curValue;
            System.out.println("Введите новые значения полей (Enter - оставить прежнее значение).");
            // Получаем новые значения полей
            System.out.print("Название (прежнее значение): " +
                    editedToy.getName() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedToy.setName(curValue);

            System.out.print("Количество (прежнее значение): " +
                    editedToy.getCount() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedToy.setCount(Integer.parseInt(curValue));

            System.out.print("Цена (прежнее значение): " +
                    editedToy.getPrice() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedToy.setPrice(Float.parseFloat(curValue));

            System.out.print("Вес (прежнее значение): " +
                    editedToy.getWeight() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedToy.setWeight(Integer.parseInt(curValue));

            // сохраняем данные в файл
            if (toysModel.save()) {
                System.out.println("Игрушка с id=" + curId + " успешно отредактирована.");
                return true;
            }

        } catch (Exception ex) {
            System.out.println("Ошибка при редактировании игрушки.\n" + ex.toString());
            return false;
        }
        return false;
    }

    // методы для обработки меню - Розыгрыш призов
    public void PrizesToAwardShowAll() {
        // Показать разыгранные призы
        DrawingModel drawingModel = new DrawingModel();
        if (drawingModel.loadPrizesToAward()) {
            drawingModel.ShowTablePrizesToAward();
        }
        ReturnToPrevPos(); // курсор меню возвращаем на предыдущее положение
        
    }

    public boolean PrizeAddNew() {
        DrawingModel drawingModel = new DrawingModel();
        if (drawingModel.PrizesToAwardAddNew())
            return true;
        return false;
    }

    public void PrizesAwardedShowAll() {
        // Показать разыгранные призы
        DrawingModel drawingModel = new DrawingModel();
        if (drawingModel.loadPrizesAwarded()) {
            drawingModel.ShowTablePrizesAwarded();
        }
        ReturnToPrevPos(); // курсор меню возвращаем на предыдущее положение
        showDrawingMenu();
    }

    public boolean PrizeSetAsAwarded() {
            // Установка признака что приз выдан

            // показать все разыгранные призы, для выбора ID
            PrizesToAwardShowAll();
            System.out.print("Введите id разыгранного приза, для смены статуса на Вручен: ");
            try {
                int curId = Integer.parseInt(sc.nextLine());

                System.out.print("Введите дату, время вручения (Enter - текущая. пример: 06.07.2023 15:10): ");
                String curValue = sc.nextLine();
                LocalDateTime curDate;
                if (curValue.equals("")) {
                    curDate = LocalDateTime.now();
                } else {
                    curDate = LocalDateTime.parse(sc.nextLine(),formatter);
                }

                DrawingModel drawingModel = new DrawingModel();
                //загружаем список разыгранных призов
                if (!drawingModel.loadPrizesToAward()) {
                    System.out.print("Ошибка при загрузке списка разыгранных призов!");
                    return false;
                }
                //находим выбранный приз
                Prize curPrize = drawingModel.getPrizeToAwardById(curId);
                //переводим приз в другую таблицу
                if (drawingModel.PrizeToAwardSetAsAwarded(curPrize, curDate)) {
                    
                    return true;
                }
            } catch (Exception ex) {
                System.out.println("Ошибка при редактировании игрушки.\n" + ex.toString());
                return false;
            }
        // выводим выданные призы с указанием id добавленной записи

        return false;
    }

    /*
     * public boolean DrawingBeginNew() {
     * return ShowNewChoice;
     * 
     * }
     */

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

    public void ResetMenuPos() {
        prevPos = "";
        choice = "";
        newPos = "";
    }

    public void ReturnToPrevPos() {
        newPos = prevPos;
        choice = "";
    }

    public Menu() {
        prevPos = "";
        choice = "";
        newPos = "";
        formatter = DateTimeFormatter.ofPattern(
            "dd.MM.yyyy HH:mm");
    }
}
