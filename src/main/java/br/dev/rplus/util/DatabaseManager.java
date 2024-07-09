package br.dev.rplus.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseManager {
    public static boolean executeSql(String dbUrl, String query) {
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            if (conn != null) {
                System.out.println("Conexão com SQLite estabelecida.");
                try (Statement stmt = conn.createStatement()) {
                    for (String command : query.split(";")) {
                        if (!command.trim().isEmpty()) {
                            stmt.execute(command + ";");
                        }
                    }
                    System.out.println("SQL executado com sucesso.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean executeSqlFile(String dbUrl, String filename) {
        String query = loadSqlFile(filename);
        return executeSql(dbUrl, query);
    }

    private static String loadSqlFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (
                InputStream is = DatabaseManager.class.getClassLoader().getResourceAsStream(fileName);
                Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())
        ) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Arquivo não encontrado: " + fileName);
        }
        return content.toString();
    }
}
