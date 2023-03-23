import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMenu {
    private Chat chat;
    private User current;
    ChatMenu(User current){
        this.current = current;
    }
    public void run(Scanner scanner , Chat chat){
        this.chat = chat;
        while(true){
            String command = scanner.nextLine();
            if(Pattern.matches(sendmessageregex , command) == true){
                Matcher matcher = sendmessagereg.matcher(command);
                sendMessage(matcher);
            }
            else if(Pattern.matches(addmemberregex , command) == true){
                Matcher matcher = addmemberreg.matcher(command);
                addMembers(matcher);
            }
            else if(Pattern.matches("show\\sall\\smessages" , command) == true){
                System.out.print(showMessages());
            }
            else if(Pattern.matches(showmembersregex , command) == true){
                System.out.print(showMembers());
            }
            else if(Pattern.matches("back" , command)){
                return;
            }
            else{
                System.out.println("Invalid command!");
            }
        }
    }
    private String showMessages(){
        String out = "";
        out += "Messages:\n";
        for(int i = 0 ; i < chat.getMessages().size() ; i++){
            out += chat.getMessages().get(i).getOwner().getName();
            out += ("(" + chat.getMessages().get(i).getOwner().getId() + "): ");
            out += ("\"" +  chat.getMessages().get(i).getContent() +"\"\n") ;
        }
        return out;
    }
    private String showMembers(){
        if(chat.getClass().getSimpleName().equals("PrivateChat")){
            return "Invalid command!\n";
        }
        String out = "";
        out += "Members:\n";
        out += ("name: " + chat.getOwnre().getName() + ", id: " + chat.getOwnre().getId() +" *owner\n");
        for(int i = 1 ; i < chat.getMembers().size() ; i++){
            out += ("name: " + chat.getMembers().get(i).getName() + ", id: " + chat.getMembers().get(i).getId() +"\n");
        }
        return out;
    }
    private void addMembers(Matcher matcher){
        matcher.find();
        String id = matcher.group("id");
        if(chat.getClass().getSimpleName().equals("PrivateChat")){
            System.out.println("Invalid command!");
            return;
        }
        else if(chat.getClass().getSimpleName().equals("Channel")||chat.getClass().getSimpleName().equals("Group")){
            if(!chat.getOwnre().equals(current)){
                System.out.println("You don't have access to add a member!");
                return;
            }
        }
        for(int i = 0 ; i < chat.getMembers().size() ; i++){
            if(chat.getMembers().get(i).getId().equals(id)){
                System.out.println("This user is already in the chat!");
                return;
            }
        }
        for(int i = 0 ; i < Messanger.getUsers().size() ; i++){
            if(Messanger.getUsers().get(i).getId().equals(id)){
                chat.addMember(Messanger.getUsers().get(i));
                Messanger.getUsers().get(i).addChat(chat);
                if(chat.getClass().getSimpleName().equals("Group")){
                    Messanger.getUsers().get(i).addGroup((Group) chat);
                    String text = Messanger.getUsers().get(i).getName() + " has been added to the group!";
                    Message message = new Message(current , text);
                    chat.addMessage(message);
                    int x = 0;
                    for(int ii = 0 ; ii < chat.getMembers().size() ; ii++){
                        for(int j = 0 ; j < chat.getMembers().get(ii).getChats().size() ; j++){
                            if(chat.getMembers().get(ii).getChats().get(j).equals(chat)){
                                Chat temp = chat.getMembers().get(ii).getChats().get(j);
                                chat.getMembers().get(ii).getChats().remove(j);
                                chat.getMembers().get(ii).getChats().add(temp);
                            }
                        }
                    }
                }
                else{
                    Messanger.getUsers().get(i).addChannel((Channel) chat);
                }
                System.out.println("User has been added successfully!");
                return;
            }
        }
        System.out.println("No user with this id exists!");
        return;
    }
    private void sendMessage(Matcher matcher){
        matcher.find();
        if(chat.getClass().getSimpleName().equals("Channel") && !chat.getOwnre().equals(current)){
            System.out.println("You don't have access to send a message!");
            return;
        }
        String message = matcher.group("message");
        Message message1 = new Message(current , message);
        chat.addMessage(message1);
        int x = 0;
        if(!chat.getClass().getSimpleName().equals("PrivateChat")){
        for(int i = 0 ; i < chat.getMembers().size() ; i++){
            for(int j = 0 ; j < chat.getMembers().get(i).getChats().size() ; j++){
                if(chat.getMembers().get(i).getChats().get(j).equals(chat)){
                    Chat temp = chat.getMembers().get(i).getChats().get(j);
                    chat.getMembers().get(i).getChats().remove(j);
                    chat.getMembers().get(i).getChats().add(temp);
                }
            }
        }}
        else{
            if(chat.getId().equals(current.getId())){
                for(int i = 0 ; i < current.getChats().size() ; i++){
                    if(current.getChats().get(i).getId().equals(chat.getId()) && current.getChats().get(i).getClass().getSimpleName().equals(chat.getClass().getSimpleName())){
                        Chat temp = current.getChats().get(i);
                        current.getChats().remove(i);
                        current.getChats().add( temp);
                    }
                }
            }
            else{
                for(int i = 0 ; i < current.getChats().size() ; i++){
                    if(current.getChats().get(i).getId().equals(chat.getId()) && chat.getClass().getSimpleName().equals(current.getChats().get(i).getClass().getSimpleName())){
                        Chat temp = current.getChats().get(i);
                        current.getChats().remove(i);
                        current.getChats().add( temp);
                    }
                }
                for(int i = 0 ; i < chat.getMembers().size() ; i++){
                    if(chat.getMembers().get(i).getId().equals(chat.getId())){
                        for(int j = 0 ; j < chat.getMembers().get(i).getChats().size() ; j++){
                            if(chat.getMembers().get(i).getChats().get(j).getId().equals(current.getId()) && chat.getClass().getSimpleName().equals(chat.getMembers().get(i).getChats().get(j).getClass().getSimpleName())){
                                for(int a = 0 ; a < chat.getMembers().get(i).getChats().get(j).getMessages().size() ; a++){
                                    if(chat.getMembers().get(i).getChats().get(j).getMessages().get(a).equals(message1)){
                                        x = 1;
                                    }
                                }
                                if(x == 0){
                                chat.getMembers().get(i).getChats().get(j).addMessage(message1);}
                                Chat temp = chat.getMembers().get(i).getChats().get(j);
                                chat.getMembers().get(i).getChats().remove(j);
                                chat.getMembers().get(i).getChats().add(temp);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Message has been sent successfully!");
        return;
    }
    String sendmessageregex = "^send\\sa\\smessage\\sc\\s(?<message>.+)$";
    Pattern sendmessagereg = Pattern.compile(sendmessageregex);
    String addmemberregex = "^add\\smember\\si\\s(?<id>[^\\s]+)$";
    Pattern addmemberreg = Pattern.compile(addmemberregex);
    String showmembersregex = "show\\sall\\smembers";
    Pattern showmemberreg = Pattern.compile(showmembersregex);

}
