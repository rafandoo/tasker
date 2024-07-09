package br.dev.rplus.util;

public class DatabaseFakerInitializer {

    private static final String DB_URL = "jdbc:sqlite:fake.db";

    private static final String SQL_FILE = "fakeDatabase.sql";

    public static void run() {
        DatabaseManager.executeSqlFile(DB_URL, SQL_FILE);
    }
}
