package classes;

import java.util.*;

//Drawing - Розыгрыш призов

public class Drawing {
    List<Prize> prizesToAward;   //Список призов которые надо вручить
    List<PrizeAwarded> prizesAwarded;   //Список призов врученных

    public Drawing() {
        this.prizesToAward = new LinkedList<>();
        this.prizesAwarded = new LinkedList<>();
    }

    public List<Prize> getPrizesToAward() {
        return prizesToAward;
    }

    public List<PrizeAwarded> getPrizesAwarded() {
        return prizesAwarded;
    }

    public void setPrizesToAward(List<Prize> prizesToAward) {
        this.prizesToAward = prizesToAward;
    }

    public void setPrizesAwarded(List<PrizeAwarded> prizesAwarded) {
        this.prizesAwarded = prizesAwarded;
    }

}
