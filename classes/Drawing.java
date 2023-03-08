package classes;

import java.util.List;

//Drawing - Розыгрыш призов

public class Drawing {
    List<Prize> prizesToAward;   //Список призов которые надо вручить
    List<Prize> prizesAwarded;   //Список призов врученных

    public Drawing(List<Prize> prizesToAward, List<Prize> prizesAwarded) {
        this.prizesToAward = prizesToAward;
        this.prizesAwarded = prizesAwarded;
    }

    public boolean GetNewPrize() {
        //разыграть ещё один приз и поместить его в список prizesToAward

        //обновить файл prizestoaward.csv

        return false;
    }

    public boolean AwardPrizeToBuyer() {
        //вручить приз его обладателю и поместить его в список prizesAwarded

        //обновить файл prizesawarded.csv
        
        return false;
    }
}
