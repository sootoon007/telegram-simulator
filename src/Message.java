public class Message {
    private User owner ;
    private String content ;
    Message(User ownre , String content){
        this.owner = ownre;
        this.content = content;
    }
    public User getOwner(){
        return owner;
    }
    public String getContent(){
        return content;
    }
}
