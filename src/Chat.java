import java.util.ArrayList;

public class Chat {
    private ArrayList<User> members = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private User ownre ;

    private String id ;

    private String name ;
    Chat(User owner , String id , String name ){
        this.ownre = owner ;
        this.id = id ;
        this.name = name ;
    }
    public void addMember(User user){
        members.add(user);
    }
    public void addMessage(Message message){
        messages.add(message);
    }
    public String getName(){
        return name;
    }
    public ArrayList<Message> getMessages(){
        return messages;
    }
    public ArrayList<User> getMembers(){
        return members;
    }
    public String getId(){
        return id;
    }
    public User getOwnre(){
        return ownre;
    }
}
