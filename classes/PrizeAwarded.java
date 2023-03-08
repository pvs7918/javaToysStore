package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrizeAwarded extends Prize {
    private LocalDateTime dateAward;             //дата время вручения приза
   
    public PrizeAwarded(int id, Buyer buyer, Toy toy, LocalDateTime dateAward) {
        super(id, buyer, toy);
        this.dateAward = dateAward;
    }

    public LocalDateTime getDateAward() {
        return dateAward;
    }
    
    public String getDateAwardString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    "dd.MM.yyyy HH:mm");
        return dateAward.format(formatter);
    }

    public void setDateAward(LocalDateTime dateAward) {
        this.dateAward = dateAward;
    }
   
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    "dd.MM.yyyy HH:mm");
        return "PrizeAwarded: [id=" + super.getId() + ",\n" +
                "Buyer=" + super.getBuyer().toString() + ",\n" +
                "Toy=" + super.getToy().toStringAsPrize() + ",\n" +
                "DateAward=" + dateAward.format(formatter) + 
                "]\n***************";
    }    

}
