package org.example.Class;

import java.util.Date;
import java.util.Objects;

public class Transaction {

    private int idTransaction;
    private String type;
    private int idAccountOrigin;
    private int idAccountDestiny;
    private Date date;
    private float amountMovement;

    public Transaction(int idTransaction, String type, int idAccountOrigin, int idAccountDestiny, Date date, float amountMovement) {
        this.idTransaction = idTransaction;
        this.type = type;
        this.idAccountOrigin = idAccountOrigin;
        this.idAccountDestiny = idAccountDestiny;
        this.date = date;
        this.amountMovement = amountMovement;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public String getType() {
        return type;
    }

    public int getIdAccountOrigin() {
        return idAccountOrigin;
    }

    public int getIdAccountDestiny() {
        return idAccountDestiny;
    }

    public Date getDate() {
        return date;
    }

    public float getAmountMovement() {
        return amountMovement;
    }



    @Override
    public String toString() {
        return "Transaction{" +
                "idTransaction='" + idTransaction + '\'' +
                ", type='" + type + '\'' +
                ", idAccountOrigin='" + idAccountOrigin + '\'' +
                ", idAccountDestiny='" + idAccountDestiny + '\'' +
                ", date='" + date + '\'' +
                ", amountMovement='" + amountMovement + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return idTransaction == that.idTransaction && idAccountOrigin == that.idAccountOrigin && idAccountDestiny == that.idAccountDestiny && Float.compare(that.amountMovement, amountMovement) == 0 && Objects.equals(type, that.type) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaction, type, idAccountOrigin, idAccountDestiny, date, amountMovement);
    }
}
