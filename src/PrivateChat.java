public class PrivateChat extends Chat{
    PrivateChat(User owner , String id , String name){
        super(owner , id , name);
    }
    public String getName(){
        return super.getName();
    }
    public String getId(){
        return super.getId();
    }
}
