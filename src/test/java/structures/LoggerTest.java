package structures;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {
    File logFile = new File("logFile.txt");
    Logger logger = new Logger(Logger.Type.CONSOLE, logFile);

    @Test
    void logWithTime() {
        logger.logWithTime("Questo è un messaggio di test!");
    }

    @Test
    void log() {
        logger.logWithTime("Questo è un messaggio di test!");
        logger.log("Questo è un secondo messaggio di test!");
    }

    @Test
    void logWithPadding() {
        logger.logWithTime("Questo è un messaggio di test!");
        logger.log("Questo è un secondo messaggio di test!");
        logger.logWithPadding("Questo è un terzo messaggio di test!");
    }
}