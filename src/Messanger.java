import sun.security.krb5.internal.crypto.Aes128;

import java.util.ArrayList;

public class Messanger {
    static private ArrayList<Channel> channels = new ArrayList<>();
    static private ArrayList<Group> groups = new ArrayList<>();
    static private ArrayList<User> users = new ArrayList<>();
    private static User currentUser ;
    public static void addChannel(Channel channel){
        channels.add(channel);
    }
    public static void addGroup(Group group){
        groups.add(group);
    }
    public static Channel getChannelByld(String id){
        for(int i = 0 ; i < channels.size() ; i++){
            if(channels.get(i).getId().equals(id)){
                return channels.get(i);
            }
        }
        return null;
    }
    public static Group getGroupByld(String id){
        for(int i = 0 ; i < groups.size() ; i++){
            if(groups.get(i).getId().equals(id)){
                return groups.get(i);
            }
        }
        return null;
    }
    public static void addUser(User user){
        users.add(user);
    }
    public static ArrayList<User> getUsers(){
        return users;
    }

    public static ArrayList<Channel> getChannels() {
        return channels;
    }
    public static ArrayList<Group> getGroups() {
        return groups;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
