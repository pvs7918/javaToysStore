package classes;

import java.time.LocalDateTime;

public class PrizeAwarded extends Prize {
    private LocalDateTime dateAward;             //дата время вручения приза
   
    public PrizeAwarded(int id, int idBuyer, int idToy, LocalDateTime dateAward) {
        super(id, idBuyer, idToy);
        this.dateAward = dateAward;
    }


}
