package model;

import classes.*;
import java.io.*;
import java.util.*;

public class BuyersModel {
    private List<Buyer> buyers; 
    private String fnameBuyers; // название файла с исходными данными

    public BuyersModel() {
        fnameBuyers = "./db/buyers.csv";
    }
    
    public void add(Buyer rec) {
        // добавление новой записи в список buyers
        buyers.add(rec);
    }
    
    public boolean deleteById(int curId) {
        //удаление записи по идентификатору
        for (Buyer item : buyers) {
            if (item.getId() == curId) {
                buyers.remove(item);
                System.out.println("Покупатель с id=" + curId + " успешно удален.");
                return true;
            }
        }
        System.out.println("Покупатель с id=" + curId + " не найден. Удаление не выполнено!");
        return false;
    }

    public boolean save() {
        // сохранение списка в файл БД
        try {
            FileWriter fr1 = new FileWriter(fnameBuyers);
            //записываем шапку таблицы
            fr1.append("id|fullName|checkNumber|phone\n");
            //основная таблица
            for (Buyer item : buyers) {
                fr1.append(item.getId() + "|" +
                        item.getFullName() + "|" +
                        item.getCheckNumber() + "|" +
                        item.getPhone() + "\n");
            }
            fr1.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean load() {
        // загрузка списка из файла БД формата csv
        buyers = new LinkedList<>();
        // открываем и читаем данные из файла
        try (FileReader fr = new FileReader(fnameBuyers)) {
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
                    //в regex | означает OR, поэтому его надо экранировать через \\
                    String[] fields = curRow.split("\\|");
                    if (fields.length != 4) {
                        throw new Exception("В исходном файле ошибка в строке " + i 
                                    + ". Количество полей не равно 4.");
                    }
                    // парсим поля
                    int curId = Integer.parseInt(fields[0].trim());
                    String curFullName = fields[1].trim();
                    String curCheckNumber = fields[2].trim();
                    String curPhone = fields[3].trim();

                    Buyer curBuyer = new Buyer(curId, curFullName, curCheckNumber,
                                                curPhone);
                    buyers.add(curBuyer);
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
    public List<Buyer> getBuyersAll() {
        return buyers;
    }

    public int getNewId() {
        int maxId = -1;
        for (Buyer item : buyers) {
            if (item.getId() > maxId)
                maxId = item.getId();
        }
        return maxId + 1;
    }

    public Buyer getBuyerById(int curBuyerId) {
        for (Buyer item : buyers) {
            if (item.getId() == curBuyerId)
                return item;
        }
        return null;
    }

    @Override
    public String toString() {
        String res = "";
        for (Buyer item : buyers) {
            res += item.toString();
        }
        return "Таблица покупателей\n---------------\n: " + res;
    }

}
