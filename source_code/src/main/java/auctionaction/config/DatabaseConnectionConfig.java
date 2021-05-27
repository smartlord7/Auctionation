package auctionaction.config;

import java.io.*;

public class DatabaseConnectionConfig implements Serializable{
    private String JDBC_PSQL_CONN_LOCAL, JDBC_USER, JDBC_PASS;

    public DatabaseConnectionConfig() {}

    public boolean loadDatabaseConfigs(String filePath) {
        File configsTxt = new File(filePath + ".txt");
        File configsBin = new File(filePath + ".dat");

        if(configsBin.isFile() && configsBin.exists()) {

            if(loadBinConfigs(configsBin)) {
                System.out.println("Connection configurations loaded successfully.");

                return true;
            }

            return false;
        } else if(loadTxtConfigs(configsTxt)) {
            System.out.println("Connection configurations loaded successfully.");

            return true;
        }

        return false;
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

    private boolean loadTxtConfigs(File file) {
        String line;
        int lineNumber = 0;

        if(file.exists() && file.isFile()) {
            try {
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
                            return false;
                    }

                    lineNumber++;
                }
                bufferedReader.close();

                return true;
            } catch (IOException exception){
                return false;

            }

        }

        return false;
    }

    private boolean loadBinConfigs(File file) {
        DatabaseConnectionConfig config;

        if(file.isFile() && file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream streamReader = new ObjectInputStream(inputStream);

                config = (DatabaseConnectionConfig) streamReader.readObject();

                this.setJDBC_USER(config.getJDBC_USER());
                this.setJDBC_PASS(config.getJDBC_PASS());
                this.setJDBC_PSQL_CONN_LOCAL(config.getJDBC_PSQL_CONN_LOCAL());

                return true;
            }  catch (IOException | ClassNotFoundException exception) {
                return false;
            }
        }

        return false;
    }

    public boolean uploadBinConfigs(String filePath) {
        File file = new File(filePath + ".dat");
        DatabaseConnectionConfig configs = new DatabaseConnectionConfig();

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream streamWriter = new ObjectOutputStream(outputStream);

            configs.setJDBC_USER(this.getJDBC_USER());
            configs.setJDBC_PASS(this.getJDBC_PASS());
            configs.setJDBC_PSQL_CONN_LOCAL(this.getJDBC_PSQL_CONN_LOCAL());

            streamWriter.writeObject(configs);

            streamWriter.close();

            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    public String toString() {
        String string = null;

        string = "User: " + getJDBC_USER() + "\nPassword: " + getJDBC_PASS() + "\nConnection: " + getJDBC_PSQL_CONN_LOCAL();

        return string;
    }
}