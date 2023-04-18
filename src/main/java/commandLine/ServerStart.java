package commandLine;

import server.MultiConnectionServer;
import structures.MessageSettings;

public class ServerStart {

    public static void main(String[] args)
    {
        MessageSettings messageSettings = new MessageSettings(3, 2);
        MultiConnectionServer multiConnectionServer = new MultiConnectionServer(messageSettings, 30080);
        multiConnectionServer.accept();
    }

}
