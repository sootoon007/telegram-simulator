import java.util.ArrayList;

public class User {
    private ArrayList<Chat> chats = new ArrayList();
    private ArrayList<Channel> channels = new ArrayList();
    private ArrayList<Group> groups = new ArrayList();
    private ArrayList<PrivateChat> privateChats = new ArrayList();

    private String id ;
    private String name ;
    private String password ;
    User(String id , String name , String password){
        this.id = id ;
        this.name = name ;
        this.password = password ;
    }
    public void addChat(Chat chat){
        chats.add(chat);
    }
    public void addGroup(Group group){
        groups.add(group);
    }
    public void addPrivateCHat(PrivateChat pv){
        privateChats.add(pv);
    }
    public void addChannel(Channel channel){
        channels.add(channel);
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }
    public ArrayList<PrivateChat> getPrivateChats() {
        return privateChats;
    }
    public ArrayList<Channel> getChannels() {
        return channels;
    }
    public ArrayList<Group> getGroups() {
        return groups;
    }
    public String getName() {
        return name;
    }
    public Channel getChannelByld(String id){
        for(int i = 0 ; i < channels.size() ; i++){
            if(channels.get(i).getId().equals(id)){
                return channels.get(i);
            }
        }
        return null;
    }
    public Group getGroupByld(String id){
        for(int i = 0 ; i < groups.size() ; i++){
            if(groups.get(i).getId().equals(id)){
                return groups.get(i);
            }
        }
        return null;
    }

}
