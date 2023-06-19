package socket;

import client.ClientSocket;
import exceptions.MessageHeaderLengthException;
import exceptions.TransferringException;
import exceptions.TransferringSettingsException;
import structures.Message;
import structures.MessageTypes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class OutputChannel {

    private final OutputStream socketOutputStream;

    private final ClientSocket socket;

    public OutputChannel(ClientSocket socket) {
        this.socket = socket;
        try {
            this.socketOutputStream = this.socket.getSocketObject().getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendSettings(Message message) {
        this.socket.getLogger().logWithTime("> Trasmissione Impostazioni... <\n");
        try {
            this.socket.getLogger().logWithPadding("Header length: " + this.socket.getMessageSettings().getHeaderSize());
            this.socket.getLogger().logWithPadding("Tipologia d'Impostazione: " + message.getType());
            this.socket.getLogger().logWithPadding("Dati da inviare: " + message);
            this.socket.getLogger().logWithPadding("Dati grezzi da inviare: " + Arrays.toString(message.getBytes()));
            this.socketOutputStream.write((message.getType() == MessageTypes.END_SETTINGS_SEPARATOR ? new byte[0] : message.getType().getBytes()));
            this.socketOutputStream.write(message.getBytes());
            this.socketOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.socket.getLogger().logWithPadding("Lunghezza dati totali inviati: " + (message.getType().getBytes().length + message.getBytes().length));
        this.socket.getLogger().logWithPaddingAndBR("> Fine Trasmissione <");
    }


    public void send(Message message) {
        this.socket.getLogger().logWithTime("> Trasmissione... <\n");
        try {
            this.socket.getLogger().logWithPadding("Header length: " + this.socket.getMessageSettings().getHeaderSize());
            if (!this.socket.getMessageSettings().checkHeaderLengthOfMessage(message)) {
                this.socket.getLogger().logWithPadding("Impossibile inviare il pacchetto");
                throw new MessageHeaderLengthException(this.socket.getMessageSettings());
            }

            this.socket.getLogger().logWithPadding("Lunghezza della tipologia del pacchetto: " + message.getType().getBytes().length);
            this.socketOutputStream.write(this.socket.getMessageSettings().getTypeMessageSize(message).getBytes());
            this.socket.getLogger().logWithPadding("Dati della tipologia del pacchetto: " + Arrays.toString(message.getType().getBytes()));
            this.socketOutputStream.write(message.getType().getBytes());
            this.socket.getLogger().logWithPadding("Lunghezza dei dati utili: " + this.socket.getMessageSettings().getDataMessageSize(message));
            this.socketOutputStream.write(this.socket.getMessageSettings().getDataMessageSize(message).getBytes());
            this.socket.getLogger().logWithPadding("Dati grezzi utili: " + Arrays.toString(message.getBytes()));
            this.socket.getLogger().logWithPadding("Dati utili: " + message);
            this.socketOutputStream.write(message.getBytes());
            this.socketOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.socket.getLogger().logWithPadding("Lunghezza dati totali inviati: " + (message.getType().getBytes().length + message.getBytes().length));
        this.socket.getLogger().logWithPaddingAndBR("> Fine Trasmissione <");
    }
}
