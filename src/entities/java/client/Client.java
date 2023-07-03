package client;

import exceptions.ExceptionHandler;
import structures.*;

/**
 * <h3>Abstract class for Client object</h3>
 * <br>
 * This is a average-level client class which includes all methods that permit to comunicate with the server. <br>
 * This class is extended both in {@link commandLineInterfaceSocket.ClientCLI} and {@link graphicInterfaceSocket.ClientGUI}. <br>
 * <p>
 * This class cannot be instantiated because its only an halfway between high and low client level.
 */
public abstract class Client {
    private Address serverAddress;

    private ClientSocket socket;

    private final SettingsCollector settingsCollector;

    private final ExceptionHandler exceptionHandler;

    public Client(SettingsCollector settingsCollector) {
        this.exceptionHandler = new ExceptionHandler();
        this.settingsCollector = settingsCollector;
    }

    public boolean connectTo(Address serverAddress) {
        if (serverAddress == null)
            return false;
        if (!serverAddress.isUsable()) {
            this.exceptionHandler.setExceptionFromAnotherExceptionHandler(serverAddress.getExceptionHandler());
            return false;
        }
        this.serverAddress = serverAddress;
        return this.connect();
    }

    /**
     * <h3>Connection method</h3>
     * <p>
     * This method is used to connect the client to the server using {@link #serverAddress}, {@link #settingsCollector}, {@link #exceptionHandler}. <br>
     *
     * @return <br>
     *
     * <ul style="margin:0; padding:0">
     *     <li>
     *         {@code true}: if a connection to the server is established and message settings are correctly sent.
     *     </li>
     *     <li>
     *         {@code false}:
     *         <ul style="margin-top:0; padding-top:0">
     *             <li>
     *                 the connection to the server is failed
     *             </li>
     *             <li>
     *                 message settings are incorrectly sent
     *             </li>
     *         </ul>
     *     </li>
     * </ul>
     */
    private boolean connect() {
        this.socket = new ClientSocket(this.serverAddress, this.settingsCollector, this.exceptionHandler);
        return socket.isConnected();
        //return this.setMessageSettings();
    }


    public boolean send(Message message) {
        return this.socket.send(message);
    }

    public Message receive() {
        return this.socket.read();
    }

    /**
     * <h3>Message settings send method</h3>
     * <p>
     * Soon after a connection is established, all the settings used to manage messages are sent to the server.
     *
     * @return <br>
     * <ul style="margin:0; padding:0">
     *     <li>
     *         {@code true}: if messages are correctly sent
     *     </li>
     *     <li>
     *         {@code false}: if messages are incorrectly sent
     *     </li>
     * </ul>
     * @see #connect()
     * @see ClientSocket#setMessageSettings(MessageSettings)
     * @deprecated This method was called by {@link #connect()}, its purpose was to send all initial settings to the connected server. Now this operation is performed automatically by {@link #connect()}.
     */
    @Deprecated
    public boolean setMessageSettings() {
        return this.socket.setMessageSettings(this.settingsCollector.getMessageSettings());
        //TODO: invio al server le nuove impostazioni
    }

    /**
     * Method used to ask the user the address used to connect to the server. <br>
     * <p>
     * This is an abstract method because its body changes base on whether this client is used via command line interface or graphic interface.
     */
    protected abstract void askAddressAndConnect();

    /**
     * Method used to start the connection process to a server.
     */
    public void run() {
        this.askAddressAndConnect();
    }

    public Logger getLogger() {
        return this.settingsCollector.getCurrentSettings().getLogger();
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }
}
