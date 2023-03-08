package classes;

public class Prize {
    private int id;             //id приза
    private Buyer buyer;        //покупатель выигрывший приз
    private Toy toy;         //идентификатор призовой игрушки
    
    public Prize(int id, Buyer buyer, Toy toy) {
        this.id = id;
        this.buyer = buyer;
        this.toy = toy;
    }

    public int getId() {
        return id;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Toy getToy() {
        return toy;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public void setToy(Toy toy) {
        this.toy = toy;
    }

    @Override
    public String toString() {
        return "Prize: [id=" + id + ",\n" +
                "Buyer=" + buyer.toString() + ",\n" +
                "Toy=" + toy.toStringAsPrize() + "]\n***************";
    }

}
