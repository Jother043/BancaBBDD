package org.example;

import org.example.Class.Client;
import org.example.Controllers.BancaBBDD;

import java.rmi.UnexpectedException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        int opcion = 0;
        BancaBBDD bancaBBDD = new BancaBBDD();
        do {
            System.out.println(menu());
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    try {
                        bancaBBDD.createTables();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:

                    System.out.println("Introduce el nombre del cliente");
                    String nombre = scanner.next();
                    System.out.println("Introduce el apellido del cliente");
                    String apellido = scanner.next();
                    System.out.println("Introduce la fecha de antigüedad del cliente");
                    String fecha = scanner.next();
                    try {
                        bancaBBDD.addRegisterUsers(nombre, apellido, fecha);
                    } catch (SQLException e) {
                        System.err.println("Error al insertar el cliente" + e.getMessage());
                        if(e.getErrorCode() == 19){
                            System.err.println("El id del cliente ya existe");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Cargando clientes...");
                    try {
                        for(Client cliente : bancaBBDD.mostrarClientes()){

                            System.out.println("Id: " + cliente.getIdCliente() + " Nombre: " + cliente.getFirtsName() + " Apellido: " + cliente.getLastName() + " Fecha: " + cliente.getAntiquity());
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al consultar los clientes");
                    }
                    System.out.println("Introduce el id del cliente");
                    int idCliente = scanner.nextInt();
                    System.out.println("Introduce el saldo de la cuenta");
                    float saldo = scanner.nextFloat();
                    try {
                        bancaBBDD.addRegisterAccounts(idCliente, saldo);
                    } catch (SQLException e) {
                        System.err.println("Error al insertar la cuenta");
                        if(e.getErrorCode() == 19){
                            System.err.println("El id de la cuenta ya existe");
                        }
                        if(e.getErrorCode() == 20){
                            System.err.println("El id del cliente no existe");
                        }
                    }

                    break;
                case 4:
                    System.out.println("Introduce el tipo de transacción");
                    String tipoTransaccion = scanner.next();
                    System.out.println("Cargando cuentas...");
                    try {
                        System.out.println(bancaBBDD.mostrarCuentas());
                    } catch (SQLException e) {
                        System.err.println("Error al consultar las cuentas");
                    }
                    System.out.println("Introduce la cuenta de origen de la transacción");
                    int idCuentaTransaccion = scanner.nextInt();
                    System.out.println("Introduce la cuenta de destino de la transacción");
                    int idCuentaDestinoTransaccion = scanner.nextInt();
                    Date fechaTransaccion = new Date(System.currentTimeMillis());
                    System.out.println("Introduce el importe de la transacción");
                    float importeTransaccion = scanner.nextFloat();
                    try {
                        bancaBBDD.addRegisterTransactions(tipoTransaccion, idCuentaTransaccion, idCuentaDestinoTransaccion,importeTransaccion, fechaTransaccion);
                    } catch (SQLException e) {
                        System.err.println("Error al insertar la transacción");
                        if(e.getErrorCode() == 19){
                            System.err.println("El id de la cuenta no existe");
                        }
                    }

                    break;
                case 5:
                    System.out.println("Introduce el id de la cuenta");
                    int idCuentaSaldo = scanner.nextInt();
                    try {
                        bancaBBDD.consultarSaldo(idCuentaSaldo);
                    } catch (SQLException e) {
                        System.err.println("Error al consultar el saldo");
                        if(e.getErrorCode() == 19){
                            System.err.println("El id de la cuenta no existe");
                        }
                    }
                    break;
                case 6:
                    try {
                        System.out.println("Cargando clientes...");
                        for(Client cliente : bancaBBDD.mostrarClientes()){

                            System.out.println("Id: " + cliente.getIdCliente() + " Nombre: " + cliente.getFirtsName() + " Apellido: " + cliente.getLastName() + " Fecha: " + cliente.getAntiquity());
                        }
                        System.out.println("Seleccione el cliente: ");
                        int idClienteTransacciones = scanner.nextInt();
                        System.out.println("Mostrando cuentas del cliente...");
                        bancaBBDD.mostrarCuentasCliente(idClienteTransacciones);
                        System.out.println("Seleccione la cuenta: ");
                        int idCuentaTransacciones = scanner.nextInt();
                        bancaBBDD.consultarHistorialTransacciones(idCuentaTransacciones);


                    } catch (SQLException e) {
                        System.err.println("Error al consultar las transacciones");
                    }
                    break;
                case 7:
                    try {
                        System.out.println("Cargando clientes...");
                        for(Client cliente : bancaBBDD.mostrarClientes()){

                            System.out.println("Id: " + cliente.getIdCliente() + " Nombre: " + cliente.getFirtsName() + " Apellido: " + cliente.getLastName() + " Fecha: " + cliente.getAntiquity());
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al consultar los clientes");
                    }
                    break;
                case 8:
                    try {
                        System.out.println("Cargando cuentas...");
                        System.out.println(bancaBBDD.mostrarCuentas());
                    } catch (SQLException e) {
                        System.err.println("Error al consultar las cuentas");
                    }
                    break;
                case 9:
                    try {
                        bancaBBDD.cargarUsuariosCuentasTransacciones();
                    } catch (SQLException e) {
                        System.err.println("Error al cargar los usuarios, cuentas y transacciones");
                    }
                    break;
                case 10:
                    System.out.println("Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }
        } while (opcion != 10);

    }

    public static String menu(){
        StringBuilder menu = new StringBuilder();
        menu.append("Bienvenido a la aplicación de Banca\n");
        menu.append("1. Crear tablas\n");
        menu.append("2. Registrar cliente\n");
        menu.append("3. Registrar cuenta\n");
        menu.append("4. Realizar transacción\n");
        menu.append("5. Consultar saldo\n");
        menu.append("6. Consultar transacciones\n");
        menu.append("7. Consultar clientes\n");
        menu.append("8. Consultar cuentas\n");
        menu.append("9. cargar usuarios, cuentas y transacciones\n");
        menu.append("10. Salir\n");
        menu.append("Seleccione una opción: ");
        return menu.toString();
    }
}

