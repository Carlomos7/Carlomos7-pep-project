package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message){
        if(!isValidMessageText(message.getMessage_text())){
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    private boolean isValidMessageText(String text){
        return text != null && !text.isBlank() && text.length() <= 255;
    }
}
