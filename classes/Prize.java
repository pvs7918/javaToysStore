package classes;

public class Prize {
    private int id;             //id приза
    private int idBuyer;        //покупатель выигрывший приз
    private int idToy;         //идентификатор призовой игрушки
    
    public Prize(int id, int idBuyer, int idToy) {
        this.id = id;
        this.idBuyer = idBuyer;
        this.idToy = idToy;
    }

    public int getId() {
        return id;
    }

    public int getIdBuyer() {
        return idBuyer;
    }

    public int getIdToy() {
        return idToy;
    }

    public void setIdBuyer(int idBuyer) {
        this.idBuyer = idBuyer;
    }

    public void setIdToy(int idToy) {
        this.idToy = idToy;
    }

    @Override
    public String toString() {
        return "Prize [id=" + id + ", idBuyer=" + idBuyer + ", idToy=" + idToy + "]";
    }

}
