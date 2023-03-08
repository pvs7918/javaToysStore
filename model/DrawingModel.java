package model;

import classes.*;
import java.io.*;
import java.util.*;

public class DrawingModel {
    private String fnamePrizesToAward; // название файла - призы вручить
    private String fnamePrizesAwarded; // название файла - призы врученные
    
    Drawing drw;   //Данные о розыгрыше призов

    public DrawingModel() {
        fnamePrizesToAward = "./db/prizestoaward.csv";
        fnamePrizesAwarded = "./db/prizesawarded.csv";
        drw = new Drawing();
    }

    public boolean loadPrizesToAward() {
        // загрузка списка из файла БД формата csv
        List<Prize> prizesToAward = new LinkedList<>();
        // открываем и читаем данные из файла
        try (FileReader fr = new FileReader(fnamePrizesToAward)) {
            Scanner scanner = new Scanner(fr);
            int i = 0; // номер строки
            while (scanner.hasNextLine()) {
                /*
                 * читаем строку исходного файла - первую строку пропускаем,
                 * id|Buyer=id;fullName;checkNumber;phone|Toy=id;name;count;price;weight
                 * это шапка с названием полей
                 */
                String curRow = scanner.nextLine();
                if (i > 0) {
                    // расщепляем строку разделителем ; на поля
                    //в regex | означает OR, поэтому его надо экранировать через \\
                    String[] fields = curRow.split("\\|");
                    if (fields.length != 3) {
                        throw new Exception("В исходном файле ошибка в строке " + i 
                                    + ". Количество полей не равно 3.");
                    }
                    // парсим поля покупателя
                    int curId = Integer.parseInt(fields[0].trim());
                    String[] buFields = fields[1].trim().split(";");
                    Buyer curBuyer = new Buyer(Integer.parseInt(buFields[0].trim()),
                                                buFields[1].trim(),
                                                buFields[2].trim(),
                                                buFields[3].trim());
                    String[] toFields = fields[2].trim().split(";");
                    //count и weight в таблице призов не храним, поэтому ставим ниже 0,
                    //чтобы воспользоваться методом Toy.ToStringAsPrize()
                    Toy curToy = new Toy(Integer.parseInt(toFields[0].trim()),
                                        toFields[1].trim(),
                                        0,
                                        Float.parseFloat(toFields[2].trim()),
                                        0);
                    Prize curPrize = new Prize(curId, curBuyer, curToy);
                    prizesToAward.add(curPrize);
                }
                i++;
            }
            drw.setPrizesToAward(prizesToAward);
            scanner.close();
        } catch (Exception ex) {
            System.out.println("Ошибка при загрузке списка призов для вручения.\n" + ex.toString());
            return false;
        }
        return true;
    }

    public void ShowTablePrizesToAward() {
        //Вывести в консоль таблицу игрушек
        String s1 = "Таблица-Разыгранные призы";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        for (Prize item : drw.getPrizesToAward()) {
            System.out.println(item.toString());
        }
    }
/* 
    public boolean PrizeAddNew() {
        // Розыгрыш очередного приза
        
        loadPrizesToAward(); //обновить данные по разыгранным призам
        ToysModel toysModel = new ToysModel();
        if (toysModel.load()) {
            toysModel.getRandomToyByWeight();
        }

        //загрузить список покупателей

        //Выбираем сначала покупателей, которые уже получили призы
        // для их исключения - чтобы не было у одного покупателя нескольких призов
        for (Prize buAwarded : drw.getPrizesToAward()) {
            buAwarded.getBuyer()
        }
        

        //List<Buyer> buAwarded = getRandomNotAwardedBuyer();

        // Выбор случайным образом покупателя, который ещё не получал приза

        //Выбор игрушки для приза
        //сначала кандидатов сформировать количество игрушек > 0.

        //Запись нового приза в таблицу призов для выдачи.
        
        //В таблице игрушек, количество уменьшить на -1
        
        //В таблице покупателей удалить выбранного,
        //чтобы он не получил несколько призов

        return true;
    }
    
   PrizesToAwardShowAll
    PrizeSetAsAwarded
    PrizesAwardedShowAll
    DrawingBeginNew*/

    /*public boolean deleteById(int curId) {
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
            fr1.append("id;fullName;checkNumber;phone\n");
            //основная таблица
            for (Buyer item : buyers) {
                fr1.append(item.getId() + ";" +
                        item.getFullName() + ";" +
                        item.getCheckNumber() + ";" +
                        item.getPhone() + "\n");
            }
            fr1.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }*/

    
    // возврат полного списка студентов без фильтрации и доп.упорядочивания
    /*public List<Buyer> getBuyersAll() {
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
    }*/

}
