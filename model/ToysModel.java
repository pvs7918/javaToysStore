package model;

import classes.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ToysModel implements DataModel<Toy> {
    private List<Toy> toys; // список игрушек
    private String path_db; // каталог c файлами БД в формате csv
    private String fnameToys; // название файла с исходными данными об игрушках

    // контакты загружаем и сохраняем в данной модели,
    // потому что так удобнее подвязать контакты к контрагентам

    public ToysModel(String path_db, String fnameToys) {
        this.path_db = path_db;
        this.fnameToys = fnameToys;
    }

    @Override
    public void add(Toy rec) {
        // добавление новой записи в список agents
        toys.add(rec);
    }

    @Override
    public void save() {
        // сохранение списка агентов в файл БД

    }

    @Override
    public List<Toy> load() {

        //отладка
        File dir = new File("."); //path указывает на директорию
        List<File> lst = new ArrayList<>();
        for ( File file : dir.listFiles() ){
            if ( file.isFile() )
                lst.add(file);
        }
        //отладка

        // загрузка списка игрушек toys из файла БД формата csv
        toys = new LinkedList<>();
        // открываем и читаем данные из файла об контрагентах
        try (FileReader fr = new FileReader(path_db + fnameToys)) {
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
                    String[] fields = curRow.split(";");
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
        }
        return toys;
    }

    // возврат полного списка студентов без фильтрации и доп.упорядочивания
    public List<Toy> getToysAll() {
        return toys;
    }

    public Toy getToyById(int curToyId) {
        for (Toy item : toys) {
            if (item.getId() == curToyId)
                return item;
        }
        return null;
    }

    @Override
    public String toString() {
        String res = "";
        for (Toy item : toys) {
            res += item.toString();
        }
        return "Toys list: [" + res + "]";
    }

}
