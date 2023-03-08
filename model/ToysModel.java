package model;

import classes.*;
import java.io.*;
import java.util.*;

public class ToysModel {
    private List<Toy> toys; // список игрушек
    private String fnameToys; // название файла с исходными данными об игрушках

    public ToysModel() {
        fnameToys = "./db/toys.csv";
    }

    public void add(Toy rec) {
        // добавление новой записи в список toys
        toys.add(rec);
    }

    public boolean deleteById(int curId) {
        // удаление записи по идентификатору
        for (Toy item : toys) {
            if (item.getId() == curId) {
                toys.remove(item);
                System.out.println("Игрушка с id=" + curId + " успешно удалена.");
                return true;
            }
        }
        System.out.println("Игрушка с id=" + curId + " не найдена. Удаление не выполнено!");
        return false;
    }

    public boolean save() {
        // сохранение списка в файл БД
        try {
            FileWriter fr1 = new FileWriter(fnameToys);
            // записываем шапку таблицы
            fr1.append("id|name|count|price|weight\n");
            // основная таблица
            for (Toy item : toys) {
                fr1.append(item.getId() + "|" +
                        item.getName() + "|" +
                        item.getCount() + "|" +
                        item.getPrice() + "|" +
                        item.getWeight() + "\n");
            }
            fr1.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean load() {
        // загрузка списка игрушек toys из файла БД формата csv
        toys = new LinkedList<>();
        // открываем и читаем данные из файла
        try (FileReader fr = new FileReader(fnameToys)) {
            Scanner scanner = new Scanner(fr);
            int i = 0; // номер строки
            while (scanner.hasNextLine()) {
                /*
                 * читаем строку исходного файла - первую строку пропускаем,
                 * это шапка с названием полей
                 */
                String curRow = scanner.nextLine();
                if (i > 0) {
                    // расщепляем строку разделителем ; на поля
                    // в regex | означает OR, поэтому его надо экранировать через \\
                    String[] fields = curRow.split("\\|");
                    if (fields.length != 5) {
                        throw new Exception("В исходном файле ошибка в строке " + i
                                + ". Количество полей не равно 5.");
                    }
                    // парсим поля
                    int curId = Integer.parseInt(fields[0].trim());
                    String curName = fields[1].trim();
                    int curCount = Integer.parseInt(fields[2].trim());
                    float curPrice = Float.parseFloat(fields[3].trim());
                    int curWeight = Integer.parseInt(fields[4].trim());

                    Toy curToy = new Toy(curId, curName, curCount,
                            curPrice, curWeight);
                    toys.add(curToy);
                }
                i++;
            }
            scanner.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    // возврат полного списка студентов без фильтрации и доп.упорядочивания
    public List<Toy> getToysAll() {
        return toys;
    }

    public int getNewId() {
        int maxId = -1;
        for (Toy item : toys) {
            if (item.getId() > maxId)
                maxId = item.getId();
        }
        return maxId + 1;
    }

    public Toy getToyById(int curToyId) {
        for (Toy item : toys) {
            if (item.getId() == curToyId)
                return item;
        }
        return null;
    }

    public Toy getRandomToyByWeight() {
        // возвращает игрушку выбранную случайным образом с учетом веса
        
        if (toys.size() == 0) {
            System.out.println("Нет игрушек!");
            return null;
        }

        int SumWt = 0;
        List<Toy> selToys = new LinkedList<>();
        // 1. Делаем выборку игрушек количество которых > 0 и находим сумму их весов
        for (Toy item : toys) {
            if (item.getCount() > 0) {
                selToys.add(item);
                SumWt += item.getWeight(); //сумма весов
            }
        }

        if (selToys.size() == 0) {
            System.out.println("Игрушки для выдачи призов закончились!");
            return null;
        }

        // 2. Берем случайное значение от 0 до TotalWt
        // вес случайный от 0 до TotalWt
        int RndWt = new Random().nextInt(SumWt + 1);
        // 3. Ищем элемент, который попал под это значение
        SumWt=0; //текущая сумма весов
        for (Toy item : selToys) {
            SumWt += item.getWeight(); //сумма весов от 0 до текущего элемента
            if (SumWt >= RndWt) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String res = "";
        for (Toy item : toys) {
            res += item.toString();
        }
        return "Таблица игрушек\n---------------\n: " + res;
    }

}
