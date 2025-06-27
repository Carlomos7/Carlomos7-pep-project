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
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public boolean deleteMessageById(int id){
        Message found = messageDAO.getMessageById(id);
        if(found == null){
            return false;
        }
        messageDAO.deleteMessageById(id);
        return true;
    }

    public Message updateMessage(int id, String text){
        if(!isValidMessageText(text)){
            return null;
        }
        Message existing = messageDAO.getMessageById(id);
        if (existing == null){
            return null;
        }
        messageDAO.updateMessageText(id, text);
        return messageDAO.getMessageById(id);
    }

    public List<Message> getMessagesByAccountId(int id){
        return messageDAO.getMessagesByAccountId(id);
    }
    
    private boolean isValidMessageText(String text){
        return text != null && !text.isBlank() && text.length() <= 255;
    }
}
