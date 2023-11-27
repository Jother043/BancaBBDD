package org.example.Class;

import java.util.Date;
import java.util.Objects;

public class Client {

    private int idCliente;
    private String firtsName;
    private String lastName;
    private Date antiquity;

    public Client() {

    }

    public Client(int idCliente, String firtsName, String lastName, Date antiquity) {
        this.idCliente = idCliente;
        this.firtsName = firtsName;
        this.lastName = lastName;
        this.antiquity = antiquity;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getFirtsName() {
        return firtsName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getAntiquity() {
        return antiquity;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setFirtsName(String firtsName) {
        this.firtsName = firtsName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAntiquity(Date antiquity) {
        this.antiquity = antiquity;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idCliente='" + idCliente + '\'' +
                ", firtsName='" + firtsName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", antiquity='" + antiquity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return idCliente == client.idCliente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente);
    }
}
