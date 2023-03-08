package model;

import classes.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DrawingModel {
    private String fnamePrizesToAward; // название файла - призы вручить
    private String fnamePrizesAwarded; // название файла - призы врученные
    DateTimeFormatter formatter; //формат даты времени
    private Drawing drw;   //Данные о розыгрыше призов

    public DrawingModel() {
        fnamePrizesToAward = "./db/prizestoaward.csv";
        fnamePrizesAwarded = "./db/prizesawarded.csv";
        drw = new Drawing();
        formatter = DateTimeFormatter.ofPattern(
            "dd.MM.yyyy HH:mm");
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
                 * id|Buyer=id;fullName;checkNumber;phone|Toy=id;name;price
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

    public boolean loadPrizesAwarded() {
        // загрузка списка из файла БД формата csv
        
        List<PrizeAwarded> prizesAwarded = new LinkedList<>();
        // открываем и читаем данные из файла
        try (FileReader fr = new FileReader(fnamePrizesAwarded)) {
            Scanner scanner = new Scanner(fr);
            int i = 0; // номер строки
            while (scanner.hasNextLine()) {
                /*
                 * читаем строку исходного файла - первую строку пропускаем,
                 * id|Buyer=id;fullName;checkNumber;phone|Toy=id;name;price|dateAward
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
                    
                    LocalDateTime curDate = LocalDateTime.parse(fields[3].trim(), formatter);
                    PrizeAwarded curPrize = new PrizeAwarded(curId, curBuyer, curToy, curDate);
                    prizesAwarded.add(curPrize);
                }
                i++;
            }
            drw.setPrizesAwarded(prizesAwarded);
            scanner.close();
        } catch (Exception ex) {
            System.out.println("Ошибка при загрузке таблицы-Призы врученные.\n" + ex.toString());
            return false;
        }
        return true;
    }

    public void ShowTablePrizesToAward() {
        //Вывести в консоль таблицу-Разыгранные призы
        String s1 = "Таблица-Разыгранные призы";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        for (Prize item : drw.getPrizesToAward()) {
            System.out.println(item.toString());
        }
    }

    public void ShowTablePrizesAwarded() {
        //Вывести в консоль таблицу-Врученные призы
        String s1 = "Таблица-Врученные призы";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        for (PrizeAwarded item : drw.getPrizesAwarded()) {
            System.out.println(item.toString());
        }
    }    
    
    public boolean PrizeToAwardSetAsAwarded(Prize curPrize, LocalDateTime curDate) {
        //перевод curPrize из таблицы PrizesToAward в PrizesAwarded-вручение приза
        int curID = curPrize.getId();
        this.loadPrizesAwarded();
        List<PrizeAwarded> prizesAwarded = drw.getPrizesAwarded();
        int newId = getPrizesAwardedNewId();

        PrizeAwarded newPrizeAwarded = new PrizeAwarded(newId,
                                                    curPrize.getBuyer(),
                                                    curPrize.getToy(),
                                                    curDate);

        //добавляем новый врученный приз к общему списку врученных призов
        if (!prizesAwarded.add(newPrizeAwarded)) {
            System.out.print("Ошибка при добавлении врученного приза!");
            return false;
        }

        //удаляем разыгранный приз
        List<Prize> prizesToAward = drw.getPrizesToAward();
        for (Prize item : prizesToAward) {
            if (item.getId() == curID) {
                prizesToAward.remove(item);
                break;
            }
        }

        //сохраняем врученные призы
        drw.setPrizesAwarded(prizesAwarded);
        savePrizesAwarded();

        // пересохраняем разыгранные призы
        drw.setPrizesToAward(prizesToAward);
        savePrizesToAward();

        System.out.println("Успешно изменен статус приза на вручен. id врученного приза=" + newId + ".");    
        return true;
    }

    public boolean PrizesToAwardAddNew() {
        // Розыгрыш очередного приза

        loadPrizesToAward(); //обновить данные по разыгранным призам

        ToysModel toysModel = new ToysModel();
        if (!toysModel.load()) {
            return false;
        }
        //Получаем случайную игрушку с учетом веса и количества > 0
        Toy RandomToy = toysModel.getRandomToyByWeight();
        if (RandomToy == null) {
            System.out.println("Ошибка. Игрушка для приза не выбрана!");
            return false;
        }
        
        //загрузить список покупателей
        BuyersModel buyersModel = new BuyersModel();
        if (!buyersModel.load()) {
            return false;
        }
        //полный список покупателей
        List<Buyer> buyersAll = buyersModel.getBuyersAll();
        //инициализация списка покупателей без призов
        List<Buyer> buyersNotAwarded = new LinkedList<>();
        // Из общего списка исключаем получивших призы
        for (Buyer buyer : buyersAll) {
            boolean isPresent = false;
            //drw.getPrizesToAward() - список покупателей с призами
            for (Prize prize : drw.getPrizesToAward()) {
                if (buyer.getId() == prize.getBuyer().getId()) {
                    isPresent = true;
                    break;
                }
            }
            //если покупателя нет в списке награжденных, то добавляем его в buyersNotAwarded
            if (!isPresent) {
                buyersNotAwarded.add(buyer);
            }
        }

        if (buyersNotAwarded.size()==0) {
            System.out.println("Приз не может быть выбран. Больше нет покупателей без призов!");
            return false;
        }

        // Выбор случайного покупателя, без приза
        int RndNumber = new Random().nextInt(buyersNotAwarded.size());
        Buyer RandomBuyer = buyersNotAwarded.get(RndNumber);
        //получаем идентификатор для новой записи приза        
        int NewId = getPrizesToAwardNewId();
        //создаем новый объект - приз для вручения
        Prize newPrize = new Prize(NewId, RandomBuyer, RandomToy);
        //добавляем новый приз в таблицу призов для выдачи.
        drw.getPrizesToAward().add(newPrize);
        //сохраняем список в файл
        if (!savePrizesToAward()) {
            System.out.println("Ошибка при сохранении списка призов для вручения!");
            return false;
        }
        //В таблице игрушек, количество уменьшить на -1
        RandomToy.setCount(RandomToy.getCount()-1);
        //Обновить данные об игрушках
        toysModel.save();

        System.out.println("Новый приз успешно разыгран! id=" + NewId + ". Смотрите таблицу разыгранных призов.");
        return true;
    }

    public boolean savePrizesToAward() {
        // сохранение списка в файл БД
        try {
            FileWriter fr1 = new FileWriter(fnamePrizesToAward);
            //записываем шапку таблицы
            fr1.append("id|Buyer=id;fullName;checkNumber;phone|Toy=id;name;price\n");
            //основная таблица
            for (Prize item : drw.getPrizesToAward()) {
                fr1.append(item.getId() + "|" +
                        item.getBuyer().toSavePrize() + "|" +
                        item.getToy().toSavePrize() + "\n");
            }
            fr1.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean savePrizesAwarded() {
        // сохранение списка в файл БД
        try {
            FileWriter fr1 = new FileWriter(fnamePrizesAwarded);
            //записываем шапку таблицы
            fr1.append("id|Buyer=id;fullName;checkNumber;phone|Toy=id;name;price|dateAward\n");
            //основная таблица
            for (PrizeAwarded item : drw.getPrizesAwarded()) {
                fr1.append(item.getId() + "|" +
                        item.getBuyer().toSavePrize() + "|" +
                        item.getToy().toSavePrize() + "|" +
                        item.getDateAwardString() + "\n");
            }
            fr1.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public int getPrizesToAwardNewId() {
        int maxId = -1;
        for (Prize item : drw.getPrizesToAward()) {
            if (item.getId() > maxId)
                maxId = item.getId();
        }
        return maxId + 1;
    }

    public int getPrizesAwardedNewId() {
        int maxId = -1;
        for (Prize item : drw.getPrizesAwarded()) {
            if (item.getId() > maxId)
                maxId = item.getId();
        }
        return maxId + 1;
    }

    public Prize getPrizeToAwardById(int curPrizeId) {
        for (Prize item : drw.getPrizesToAward()) {
            if (item.getId() == curPrizeId)
                return item;
        }
        return null;
    }
/* 
    @Override
    public String toString() {
        String res = "";
        for (Buyer item : buyers) {
            res += item.toString();
        }
        return "Таблица покупателей\n---------------\n: " + res;
    }*/

}
