package commandLineInterfaceSocket;

import client.Client;
import structures.Address;
import structures.SettingsCollector;

import java.util.Scanner;

public class ClientCLI extends Client {
    public ClientCLI(SettingsCollector messageSettings) {
        super(messageSettings);
    }


    @Override
    protected void askAddressAndConnect() {
        boolean keepAsking;
        do {
            this.getLogger().log("Enter the address of the server to connect to. Use the following format: xxx.yyy.zzz.kkk:pppp");
            Scanner scanner = new Scanner(System.in);
            String addressAsString = scanner.nextLine();
            Address serverAddress = Address.getAddressFromString(addressAsString);
            keepAsking = !this.connectTo(serverAddress);
            if (keepAsking) {
                this.getLogger().logWithTime("Failed to connect because: ");
                this.getExceptionHandler().printError(this.getLogger());
            }
        } while (keepAsking);
    }

    @Override
    public void run() {
        super.run();

    }
}
