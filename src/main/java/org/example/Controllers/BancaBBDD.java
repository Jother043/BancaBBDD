package org.example.Controllers;

import org.example.Class.Client;
import org.example.Connection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancaBBDD {

    public static Connection getConnection() throws SQLException {
        DataBaseConnection connection;
        connection = DataBaseConnection.getInstance();
        return connection.getConexion();
    }

    public void createTables() throws SQLException {
        //Create a table in the database.
        //first table is for the transactions of the bank accounts and its atributtes are idTransaccion,tipo,idCuentaOrigen,idCuentaDestino,fecha,cantidad float.
        String sql1 = "CREATE TABLE IF NOT EXISTS transacciones (\n"
                + "	id integer PRIMARY KEY AUTO_INCREMENT,\n"
                + "	tipo text NOT NULL,\n"
                + "	id_cuenta_origen integer NOT NULL,\n"
                + "	id_cuenta_destino integer NOT NULL,\n"
                + "	fecha text NOT NULL,\n"
                + "	cantidad float NOT NULL,\n"
                + "	FOREIGN KEY (id_cuenta_origen) REFERENCES cuentas(id),\n"
                + "	FOREIGN KEY (id_cuenta_destino) REFERENCES cuentas(id)\n"
                + ");";
        //second table is for the bank accounts and its atributtes are idCuenta,idCliente,saldo.
        String sql2 = "CREATE TABLE IF NOT EXISTS cuentas (\n"
                + "	id integer PRIMARY KEY AUTO_INCREMENT,\n"
                + "	id_cliente integer NOT NULL,\n"
                + "	saldo float NOT NULL,\n"
                + "	FOREIGN KEY (id_cliente) REFERENCES clientes(id)\n"
                + ");";
        //third table is for the clients of the bank and its atributtes are idCliente,nombre,apellidos and antiguedad.
        String sql3 = "CREATE TABLE IF NOT EXISTS clientes (\n"
                + "	id integer PRIMARY KEY AUTO_INCREMENT,\n"
                + "	nombre text NOT NULL,\n"
                + "	apellidos text NOT NULL,\n"
                + "	antiguedad date NOT NULL\n"
                + ");";

        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute(sql3);
            statement.execute(sql2);
            statement.execute(sql1);


        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según sea necesario
        }
    }

    /**
     * This method adds a client to the database of the bank with the parameters id,nombre,apellidos,antiguedad.
     * If the id of the client already exists in the database it will throw an exception.
     * @param nombre;
     * @param apellidos;
     * @param antiguedad;
     * @throws SQLException;
     */
    public void addRegisterUsers(String nombre, String apellidos, String antiguedad) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO clientes VALUES (?,?,?,?)")) {
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, apellidos);
            preparedStatement.setString(4, antiguedad);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * This method adds a bank account to the database of the bank with the parameters id,idCliente,saldo.
     * If the id of the bank account already exists in the database it will throw an exception.
     * @param idCliente;
     * @param saldo;
     * @throws SQLException;
     */
    public void addRegisterAccounts(int idCliente, float saldo) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO cuentas VALUES (?,?,?)")) {
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, idCliente);
            preparedStatement.setFloat(3, saldo);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * This method adds a transaction to the database of the bank with the parameters id,tipo,idCuentaOrigen,idCuentaDestino,cantidad,fecha.
     * And it also updates the balance of the bank accounts.
     * @param tipo;
     * @param idCuentaOrigen;
     * @param idCuentaDestino;
     * @param cantidad;
     * @param fecha;
     * @throws SQLException;
     */
    public void addRegisterTransactions(String tipo, int idCuentaOrigen, int idCuentaDestino, float cantidad, Date fecha) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO transacciones VALUES (?,?,?,?,?,?)")) {
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, tipo);
            preparedStatement.setInt(3, idCuentaOrigen);
            preparedStatement.setInt(4, idCuentaDestino);
            preparedStatement.setDate(5, fecha);
            preparedStatement.setFloat(6, cantidad);
            preparedStatement.executeUpdate();
        }
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE cuentas SET saldo = saldo - ? WHERE id = ?")) {
            preparedStatement.setFloat(1, cantidad);
            preparedStatement.setInt(2, idCuentaOrigen);
            preparedStatement.executeUpdate();
        }
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE cuentas SET saldo = saldo + ? WHERE id = ?")) {
            preparedStatement.setFloat(1, cantidad);
            preparedStatement.setInt(2, idCuentaDestino);
            preparedStatement.executeUpdate();
        }
    }

    public void consultarSaldo(int idCuenta) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT saldo FROM cuentas WHERE id = ?")) {
            preparedStatement.setInt(1, idCuenta);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("El saldo de la cuenta " + idCuenta + " es: " + resultSet.getFloat("saldo"));
            }
        }
    }

    public void consultarHistorialTransacciones(int idCuenta) throws SQLException {
        //Consultar el historial de transacciones de una cuenta determinada.
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM transacciones WHERE id_cuenta_origen = ? OR id_cuenta_destino = ?")) {
            preparedStatement.setInt(1, idCuenta);
            preparedStatement.setInt(2, idCuenta);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("id_cuenta_origen") == idCuenta) {
                    System.out.print("Transacciones de retirada de dinero de la cuenta " + idCuenta + " [ ");
                    System.out.println("id: " + resultSet.getInt("id") + " tipo: " + resultSet.getString("tipo") + " id_cuenta_origen: " + resultSet.getInt("id_cuenta_origen") + " id_cuenta_destino: " + resultSet.getInt("id_cuenta_destino") + " fecha: " + resultSet.getDate("fecha") + " cantidad: " + resultSet.getFloat("cantidad") + " ]");
                } else if (resultSet.getInt("id_cuenta_destino") == idCuenta) {
                    System.out.print("Transacciones de ingreso de dinero de la cuenta " + idCuenta + " [ ");
                    System.out.println("id: " + resultSet.getInt("id") + " tipo: " + resultSet.getString("tipo") + " id_cuenta_origen: " + resultSet.getInt("id_cuenta_origen") + " id_cuenta_destino: " + resultSet.getInt("id_cuenta_destino") + " fecha: " + resultSet.getDate("fecha") + " cantidad: " + resultSet.getFloat("cantidad") + " ]");
                }

            }
        }
    }

    public List<Client> mostrarClientes() throws SQLException {
        List<Client> clients = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM clientes")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setIdCliente(resultSet.getInt("id"));
                client.setFirtsName(resultSet.getString("nombre"));
                client.setLastName(resultSet.getString("apellidos"));
                client.setAntiquity(resultSet.getDate("antiguedad"));
                clients.add(client);
            }
        }
        return clients;
    }

    public void mostrarCuentasCliente(int idCliente) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM cuentas WHERE id_cliente = ?")) {
            preparedStatement.setInt(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("id: " + resultSet.getInt("id") + " id_cliente: " + resultSet.getInt("id_cliente") + " saldo: " + resultSet.getFloat("saldo"));
            }
        }
    }

    public List mostrarCuentas() throws SQLException {
        List<Integer> cuentas = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT id FROM cuentas")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cuentas.add(resultSet.getInt("id"));
            }
        }
        return cuentas;
    }

    public void cargarUsuariosCuentasTransacciones()throws SQLException{

            addRegisterUsers("Juan", "García", "2020-01-01");
            addRegisterUsers("Pedro", "García", "2020-01-01");
            addRegisterUsers("Luis", "García", "2020-01-01");
            addRegisterUsers("Ana", "García", "2020-01-01");
            addRegisterUsers("María", "García", "2020-01-01");

            addRegisterAccounts(1, 1000);
            addRegisterAccounts(1, 2000);
            addRegisterAccounts(2, 1000);
            addRegisterAccounts(3, 1000);
            addRegisterAccounts(4, 1000);
            addRegisterAccounts(5, 1000);

    }
}
