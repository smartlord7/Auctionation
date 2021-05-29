package auctionaction.config;

import java.io.*;

public class DatabaseConnectionConfig implements Serializable{
    private String JDBC_PSQL_CONN_LOCAL, JDBC_USER, JDBC_PASS;

    public DatabaseConnectionConfig() {}

    public void loadDatabaseConfigs(String filePath) throws IOException, ClassNotFoundException {
        File configsTxt = new File(filePath + ".txt");
        File configsBin = new File(filePath + ".dat");

        if (configsBin.isFile() && configsBin.exists()) {
            loadBinConfigs(configsBin);
            System.out.println("Connection configurations loaded successfully.");
        } else {
            loadTxtConfigs(configsTxt);
            System.out.println("Connection configurations loaded successfully.");

        }

    }

    public String getJDBC_PASS() {
        return JDBC_PASS;
    }

    public void setJDBC_PASS(String JDBC_PASS) {
        this.JDBC_PASS = JDBC_PASS;
    }

    public String getJDBC_PSQL_CONN_LOCAL() {
        return JDBC_PSQL_CONN_LOCAL;
    }

    public void setJDBC_PSQL_CONN_LOCAL(String JDBC_PSQL_CONN_LOCAL) {
        this.JDBC_PSQL_CONN_LOCAL = JDBC_PSQL_CONN_LOCAL;
    }

    public String getJDBC_USER() {
        return JDBC_USER;
    }

    public void setJDBC_USER(String JDBC_USER) {
        this.JDBC_USER = JDBC_USER;
    }

    private void loadTxtConfigs(File file) throws IOException {
        String line;
        int lineNumber = 0;

        if(file.exists() && file.isFile()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((line = bufferedReader.readLine()) != null && line.length() != 0) {

                    switch (lineNumber) {
                        case 0:
                            JDBC_USER = line;
                            break;
                        case 1:
                            JDBC_PASS = line;
                            break;

                        case 2:
                            JDBC_PSQL_CONN_LOCAL = line;
                            break;
                        default:
                            //configurations file is not valid
                            return;
                    }

                    lineNumber++;
                }
                bufferedReader.close();
        }
    }

    private void loadBinConfigs(File file) throws IOException, ClassNotFoundException {
        DatabaseConnectionConfig config;

        if(file.isFile() && file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            ObjectInputStream streamReader = new ObjectInputStream(inputStream);

            config = (DatabaseConnectionConfig) streamReader.readObject();

            this.setJDBC_USER(config.getJDBC_USER());
            this.setJDBC_PASS(config.getJDBC_PASS());
            this.setJDBC_PSQL_CONN_LOCAL(config.getJDBC_PSQL_CONN_LOCAL());

        }
    }

    public void writeBinConfigs(String filePath) {
        File file = new File(filePath + ".dat");

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream streamWriter = new ObjectOutputStream(outputStream);

            streamWriter.writeObject(this);

            streamWriter.close();

        } catch (IOException exception) {
            System.out.println("Error saving database connection settings.");
        }
    }

    public String toString() {
        String string = null;

        string = "User: " + getJDBC_USER() + "\nPassword: " + getJDBC_PASS() + "\nConnection: " + getJDBC_PSQL_CONN_LOCAL();

        return string;
    }
}