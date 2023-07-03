package socket;

import client.ClientSocket;
import exceptions.MessageHeaderLengthException;
import exceptions.TransferringException;
import exceptions.TransferringSettingsException;
import structures.Message;
import structures.MessageType;

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

    public boolean sendSettings(Message message) {
        this.socket.getLogger().logWithTime("> Transmission Settings... <\n");
        try {
            this.socket.getLogger().logWithPadding("Header length: " + this.socket.getMessageSettings().getHeaderLength());
            this.socket.getLogger().logWithPadding("Setting name: " + message.getType());
            this.socket.getLogger().logWithPadding("Data to send: " + message);
            this.socket.getLogger().logWithPadding("Raw data to send: " + Arrays.toString(message.getBytes()));
            this.socketOutputStream.write((message.getType() == MessageType.END_SETTINGS_SEPARATOR ? new byte[0] : message.getType().getBytes()));
            this.socketOutputStream.write(message.getBytes());
            this.socketOutputStream.flush();
        } catch (IOException e) {
            this.socket.getExceptionHandler().setException(new TransferringSettingsException());
            return false;
        }
        this.socket.getLogger().logWithPadding("Total bytes of message sent: " + ((message.getType() == MessageType.END_SETTINGS_SEPARATOR ? new byte[0] : message.getType().getBytes()).length + message.getBytes().length));
        this.socket.getLogger().logWithPaddingAndBR("> End Transmission Settings <");
        return true;
    }


    public boolean send(Message message) {
        this.socket.getLogger().logWithTime("> Transmission... <\n");
        try {
            this.socket.getLogger().logWithPadding("Header length: " + this.socket.getMessageSettings().getHeaderLength());
            if (!this.socket.getMessageSettings().checkHeaderLengthOfMessage(message)) {
                this.socket.getLogger().logWithPadding("Unable to send the message");
                this.socket.getExceptionHandler().setException(new MessageHeaderLengthException(this.socket.getMessageSettings()));
                return false;
            }
            this.socket.getLogger().logWithPadding("Message data type length: " + message.getType().getBytes().length);
            this.socketOutputStream.write(this.socket.getMessageSettings().getTypeMessageLength(message).getBytes());
            this.socket.getLogger().logWithPadding("Message data type: " + Arrays.toString(message.getType().getBytes()));
            this.socketOutputStream.write(message.getType().getBytes());
            this.socket.getLogger().logWithPadding("Message data length: " + this.socket.getMessageSettings().getDataMessageLength(message));
            this.socketOutputStream.write(this.socket.getMessageSettings().getDataMessageLength(message).getBytes());
            this.socket.getLogger().logWithPadding("Raw message data: " + Arrays.toString(message.getBytes()));
            this.socket.getLogger().logWithPadding("Message data: " + message);
            this.socketOutputStream.write(message.getBytes());
            this.socketOutputStream.flush();
        } catch (IOException e) {
            this.socket.getExceptionHandler().setException(new TransferringException());
            return false;
        }
        this.socket.getLogger().logWithPadding("Total bytes of message sent: " + (this.socket.getMessageSettings().getTypeMessageLength(message).getBytes().length +
                message.getType().getBytes().length + this.socket.getMessageSettings().getDataMessageLength(message).getBytes().length + message.getBytes().length));
        this.socket.getLogger().logWithPaddingAndBR("> End Transmission <");
        return true;
    }
}
