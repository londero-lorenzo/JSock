package structures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/*
    TODO: AGGIUNGERE LOGGER
    Lato client:
    aggiungere alle impostazioni dei messaggi il logger così da salvare il tutto su file di testo e console/grafica

    Lato server:
    aggiungere logger di sistema per la ricezione delle richieste
    aggiungere logger singolo per ogni client connesso





 */

public class Logger {
    public enum Type {
        CONSOLE,
        GRAFIC,
        FILE
    }

    private boolean enable;
    private final Type type;


    //TODO: verificare che logFile sia utilizzato come variabile, altrimenti lasciare come variabile privata solamente fileWriter
    private File logFile = null;


    public Logger(Type type) {
        this.type = type;
        this.enable = true;
    }


    public Logger(Type type, File logFile) {
        this.type = type;
        this.logFile = logFile;
        try {
            logFile.createNewFile();
            FileWriter fileWriter = new FileWriter(this.logFile, true);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            fileWriter.write(
                    "--------------------------------------------------\n" +
                            "                    " + dateFormat.format(date) + "                    \n" +
                            "--------------------------------------------------\n"
            );
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void logWithTime(String message) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        this.write("[" + dateFormat.format(date) + "]: " + message);
    }


    public void log(String message) {
        this.write(message);
    }

    public void logWithPadding(String message) {
        this.write("            " + message);
    }

    public void logWithPaddingAndBR(String message) {
        this.write("\n            " + message);
    }

    private void write(String message) {
        if (this.type == Type.CONSOLE)
            System.out.println(message);
        if (this.type == Type.FILE || this.logFile != null) {
            try {
                FileWriter fileWriter = new FileWriter(this.logFile, true);
                fileWriter.write(message + "\n");
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void disable() {
        this.enable = false;
    }


}
