package org.example;

import java.sql.*;
import com.mongodb.client.*;
import org.bson.Document;

public class BancoApp {

    // Detalles de la base de datos PostgreSQL
    static final String pgURL = "jdbc:postgresql://examenad.ci66saah1axn.us-east-1.rds.amazonaws.com/herill";
    static final String pgUser = "postgres";
    static final String pgPassword = "qwerty1234";

    // Detalles de la base de datos MongoDB
    static final String mongoURL = "mongodb://herill:qwerty1234@ec2-54-146-188-92.compute-1.amazonaws.com/herill";
    static final String mongoCollection = "clients";

    public static void main(String[] args) {
        try {
            // Ejemplo de uso de los métodos
            createAccount(String.valueOf(1), "ES1600812569102753685295", 1000);  // Donde 1 es el ID del cliente
            createClient(String.valueOf(1), "cliente1@gmail.com", "Dirección Cliente 1");  // Aquí también usas el ID del cliente

            // Llamada al método transaction
            transaction("ES1600812569102753685295", "ES2131908836405121554653", 500);

            // Si llegamos aquí sin lanzar excepciones, todo ha ido correctamente
            System.out.println("Operaciones completadas exitosamente.");

        } catch (Exception e) {
            // Si se lanza alguna excepción, imprímela en la consola
            e.printStackTrace();
        }
    }
    // Método para conectar a PostgreSQL
    static Connection connectToPostgreSQL() throws SQLException {
        return DriverManager.getConnection(pgURL, pgUser, pgPassword);
    }

    // Método para conectar a MongoDB
    static MongoClient connectToMongoDB() {
        return MongoClients.create(mongoURL);
    }

    // Métodos para operaciones en PostgreSQL

    static void createAccount(String clientId, String iban, double balance) {
        try (Connection connection = connectToPostgreSQL()) {
            // Verificar si ya existe un registro con la misma IBAN
            int existingClientId = getClientIdByIban(connection, iban);

            if (existingClientId > 0) {
                // Si ya existe, actualizar el balance del registro existente
                updateAccountBalance(existingClientId, balance);
            } else {
                // Si no existe, insertar un nuevo registro
                insertNewAccount(clientId, iban, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener el clientid por IBAN
    static int getClientIdByIban(Connection connection, String iban) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT clientid FROM accounts WHERE iban = ?")) {
            preparedStatement.setString(1, iban);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("clientid") : 0;
            }
        }
    }

    // Método para insertar un nuevo registro en accounts
    static void insertNewAccount(String clientId, String iban, double balance) {
        try (Connection connection = connectToPostgreSQL();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO accounts (clientid, iban, balance) VALUES (?, ?, ?)")) {
            // Convertir clientId a entero
            int clientIdInt = Integer.parseInt(clientId);

            preparedStatement.setInt(1, clientIdInt);
            preparedStatement.setString(2, iban);
            preparedStatement.setDouble(3, balance);
            preparedStatement.executeUpdate();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar el balance de un registro existente
    static void updateAccountBalance(int clientId, double balance) {
        try (Connection connection = connectToPostgreSQL();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE accounts SET balance = ? WHERE clientid = ?")) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    // Método para obtener el clientid por nombre
    static int getClientIdByName(Connection connection, String clientName) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT clientid FROM clients WHERE name = ?")) {
            preparedStatement.setString(1, clientName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("clientid");
                } else {
                    // Manejar el caso donde el cliente no existe
                    throw new SQLException("Cliente no encontrado: " + clientName);
                }
            }
        }
    }




    static void deleteAccount(int accountId) {
        try (Connection connection = connectToPostgreSQL();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM accounts WHERE id = ?")) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Métodos para operaciones en MongoDB

    static void createClient(String name, String email, String address) {
        try (MongoClient mongoClient = connectToMongoDB()) {
            MongoDatabase database = mongoClient.getDatabase("herill");
            MongoCollection<Document> collection = database.getCollection(mongoCollection);

            Document clientData = new Document("name", name)
                    .append("email", email)
                    .append("address", address);

            collection.insertOne(clientData);
        }
    }

    static void deleteClient(String clientId) {
        try (MongoClient mongoClient = connectToMongoDB()) {
            MongoDatabase database = mongoClient.getDatabase("herill");
            MongoCollection<Document> collection = database.getCollection(mongoCollection);

            collection.deleteOne(new Document("_id", clientId));
        }
    }

    // Método para la transacción
    static void transaction(String sourceIban, String destinationIban, double amount) {
        try (Connection connection = connectToPostgreSQL()) {
            // Iniciar una transacción en PostgreSQL
            connection.setAutoCommit(false);

            // Obtener los saldos actuales de las cuentas de origen y destino
            double sourceBalance = getAccountBalance(connection, sourceIban);
            double destinationBalance = getAccountBalance(connection, destinationIban);

            // Verificar si hay suficiente saldo en la cuenta de origen
            if (sourceBalance >= amount) {
                // Actualizar saldos
                updateAccountBalance(connection, sourceIban, sourceBalance - amount);
                updateAccountBalance(connection, destinationIban, destinationBalance + amount);

                // Confirmar la transacción
                connection.commit();
                System.out.println("Transacción completada exitosamente.");

            } else {
                // Si no hay suficiente saldo en la cuenta de origen, hacer rollback
                connection.rollback();
                System.out.println("Error: Saldo insuficiente en la cuenta de origen.");
            }

        } catch (SQLException e) {
            // Manejar cualquier excepción de SQL
            e.printStackTrace();
        }
    }

    static double getAccountBalance(Connection connection, String iban) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM accounts WHERE iban = ?")) {
            preparedStatement.setString(1, iban);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("balance");
                } else {
                    // Manejar el caso donde la cuenta no existe
                    throw new SQLException("Cuenta no encontrada: " + iban);
                }
            }
        }
    }

    static void updateAccountBalance(Connection connection, String iban, double newBalance) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE iban = ?")) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setString(2, iban);
            preparedStatement.executeUpdate();
        }
    }
}
