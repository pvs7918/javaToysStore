package classes;

public class Buyer {
    private int id;
    private String fullName;        //ФИО покупателя
    private String checkNumber;     //номер чека, по которому при розыгрыше
                                    //определяется победитель
    private String phone;           //номер телефона для обратной связи с покупателем
    public int getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public String getCheckNumber() {
        return checkNumber;
    }
    public String getPhone() {
        return phone;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Buyer(int id, String fullName, String checkNumber, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.checkNumber = checkNumber;
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "[id=" + id + ", fullName=" + fullName + ", checkNumber=" + 
                checkNumber + ", phone=" + phone + "]";
    }

    public String toSavePrize() {
        return id + ";" + fullName + ";" + checkNumber + ";" + phone;
    }
}
