package org.example.Class;

import java.util.Objects;

public class Account {

    private int idCuenta;
    private int idCliente;
    private float saldo;

    public Account(int idCuenta, int idCliente, float saldo) {
        this.idCuenta = idCuenta;
        this.idCliente = idCliente;
        this.saldo = saldo;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idCuenta='" + idCuenta + '\'' +
                ", idCliente='" + idCliente + '\'' +
                ", saldo='" + saldo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return idCuenta == account.idCuenta && idCliente == account.idCliente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCuenta, idCliente);
    }
}
