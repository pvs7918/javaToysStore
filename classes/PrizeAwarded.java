package classes;

import java.time.LocalDateTime;

public class PrizeAwarded extends Prize {
    private LocalDateTime dateAward;             //дата время вручения приза
   
    public PrizeAwarded(int id, Buyer buyer, Toy toy, LocalDateTime dateAward) {
        super(id, buyer, toy);
        this.dateAward = dateAward;
    }

    public LocalDateTime getDateAward() {
        return dateAward;
    }

    public void setDateAward(LocalDateTime dateAward) {
        this.dateAward = dateAward;
    }
   

}
