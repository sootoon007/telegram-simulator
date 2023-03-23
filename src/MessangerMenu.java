import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessangerMenu {
    private Chat chat ;
    private User currentUser ;


    MessangerMenu(User currentUser) {
        this.currentUser = currentUser;
    }
    public void run(Scanner scanner){
        while(true) {
            String command = scanner.nextLine();
            if(Pattern.matches(showAllchannelregex, command) == true) {
                System.out.print(showAllChannels());
            }
            else if(Pattern.matches(createNewchannelregex, command) == true) {
                Matcher matcher = creatnewchannelreg.matcher(command);
                System.out.println(creatChannel(matcher));
            }
            else if(Pattern.matches(joinchannelregex , command) == true){
                Matcher matcher = joinchannelreg.matcher(command);
                System.out.println(joinChannel(matcher));
            }
            else if(Pattern.matches(createNewgroupregex , command) == true){
                //System.out.println("salam");
                Matcher matcher = creatnewgroupreg.matcher(command);
                System.out.println(creatGroup(matcher));
            }
            else if(Pattern.matches(createpvregex , command) == true){
                Matcher matcher = createpvreg.matcher(command);
                System.out.println(creatPrivateChat(matcher));
            }
            else if(Pattern.matches("logout" , command) == true){
                System.out.println("Logged out");
                return;
            }
            else if(Pattern.matches(enterchatregex , command) == true){
                Matcher matcher = enterchatreg.matcher(command);
                String exit = enterChat(matcher , scanner);
                if(!exit.equals("#####")){
                    System.out.println(exit);
                }
            }
            else if(Pattern.matches(showmychatsregex , command) == true){
                System.out.print(ShowChats());
            }
            else{
                System.out.println("Invalid command!");
            }
        }
    }
    private String showAllChannels(){
        String resault = "All channels:\n";
        for(int i = 0 ; i < Messanger.getChannels().size() ; i++){
            resault += (i+1) + ". ";
            resault += Messanger.getChannels().get(i).getName();
            resault += ", id: ";
            resault += Messanger.getChannels().get(i).getId();
            resault += ", members: ";
            resault += Messanger.getChannels().get(i).getMembers().size();
            resault += "\n";

        }
        return resault;
    }
    private String ShowChats(){
        String out = "";
        out += "Chats:\n";
        for(int i = currentUser.getChats().size() ; i >= 1 ; i--){
            String chattype = currentUser.getChats().get(i-1).getClass().getSimpleName();
            int z = currentUser.getChats().size() - (i-1) ;
            String chatype = "";
            if(chattype.equals("Group")){
                chatype = "group";
            }
            else if(chattype.equals("Channel")){
                chatype = "channel";
            }
            else if(chattype.equals("PrivateChat")){
                chatype = "private chat";
            }
            out += (z + ". " + currentUser.getChats().get(i-1).getName() + ", id: " + currentUser.getChats().get(i-1).getId() +", " + chatype +"\n");
        }
        return out;
    }
    private String enterChat(Matcher matcher , Scanner scanner){
        matcher.find();
        String id = matcher.group("id");
        String chattype = matcher.group("chattype");
        if(chattype.equals("private chat")){
            for(int i = 0 ; i < currentUser.getPrivateChats().size() ; i++){
                if(currentUser.getPrivateChats().get(i).getId().equals(id)){
                    System.out.println("You have successfully entered the chat!");
                    ChatMenu chatMenu = new ChatMenu(currentUser);
                    chatMenu.run(scanner , currentUser.getPrivateChats().get(i));
                    return "#####";
                }
            }
            return "You have no " + chattype + " with this id!";
        }
        else if(chattype.equals("channel")){
            for(int i = 0 ; i < currentUser.getChannels().size() ; i++){
                if(currentUser.getChannels().get(i).getId().equals(id)){
                    System.out.println("You have successfully entered the chat!");
                    ChatMenu chatMenu = new ChatMenu(currentUser);
                    chatMenu.run(scanner , currentUser.getChannels().get(i));
                    return "#####";
                }
            }
            return "You have no " + chattype + " with this id!";
        }
        else {
            for(int i = 0 ; i < currentUser.getGroups().size() ; i++){
                if(currentUser.getGroups().get(i).getId().equals(id)){
                    System.out.println("You have successfully entered the chat!");
                    ChatMenu chatMenu = new ChatMenu(currentUser);
                    chatMenu.run(scanner , currentUser.getGroups().get(i));
                    return "#####";
                }
            }
            return "You have no " + chattype + " with this id!";
        }

    }
    private String creatChannel(Matcher matcher){
        matcher.find();
        String name = matcher.group("name");
        String id = matcher.group("id");
        if(Pattern.matches("^[a-zA-Z0-9\\_]+$" , name ) == false ){
            return "Channel name's format is invalid!";
        }
        for(int i = 0 ; i < Messanger.getChannels().size() ; i++){
            if(Messanger.getChannels().get(i).getId().equals(id)){
                return "A channel with this id already exists!";
            }
        }
        Channel temp = new Channel(currentUser , id , name);
        temp.addMember(currentUser);
        currentUser.addChannel(temp);
        currentUser.addChat(temp);
        Messanger.addChannel(temp);
        return "Channel " + name + " has been created successfully!";
    }
    private String creatGroup(Matcher matcher){
        matcher.find();
        String id = matcher.group("id");
        String name = matcher.group("name");
        if(Pattern.matches("^[a-zA-Z0-9\\_]+$" , name ) == false ){
            return "Group name's format is invalid!";
        }
        for(int i = 0 ; i < Messanger.getGroups().size() ; i++){
            if(Messanger.getGroups().get(i).getId().equals(id)){
                return "A group with this id already exists!";
            }
        }
        Group temp = new Group(currentUser , id , name);
        temp.addMember(currentUser);
        currentUser.addGroup(temp);
        currentUser.addChat(temp);
        Messanger.addGroup(temp);
        return "Group " + name + " has been created successfully!";
    }
    private String creatPrivateChat(Matcher matcher){
        matcher.find();
        String id = matcher.group("id");
        for(int i = 0 ; i < Messanger.getUsers().size() ; i++){
            if(Messanger.getUsers().get(i).getId().equals(id)){
                for(int j = 0 ; j < currentUser.getChats().size() ; j++){
                    if(currentUser.getChats().get(j).getId().equals(id) && currentUser.getChats().get(j).getClass().getSimpleName().equals("PrivateChat")){
                        return "You already have a private chat with this user!";
                    }
                }
                PrivateChat Pv1 = new PrivateChat(currentUser , id , Messanger.getUsers().get(i).getName());
                Pv1.addMember(currentUser);
                Pv1.addMember(Messanger.getUsers().get(i));
                currentUser.addPrivateCHat(Pv1);
                currentUser.addChat(Pv1);
                if(!currentUser.getId().equals(Messanger.getUsers().get(i).getId())) {
                    PrivateChat Pv2 = new PrivateChat(Messanger.getUsers().get(i), currentUser.getId(), currentUser.getName());
                    Pv2.addMember(Messanger.getUsers().get(i));
                    Pv2.addMember(currentUser);
                    Messanger.getUsers().get(i).addPrivateCHat(Pv2);
                    Messanger.getUsers().get(i).addChat(Pv2);
                }
                return "Private chat with " + Messanger.getUsers().get(i).getName() + " has been started successfully!";
            }
        }
        return "No user with this id exists!";
    }
    private String joinChannel(Matcher matcher){
        matcher.find();
        String id = matcher.group("id");
        int existenc = -1;
        for(int i = 0 ; i < Messanger.getChannels().size() ; i++){
            if(Messanger.getChannels().get(i).getId().equals(id)){
                for(int j = 0 ; j < Messanger.getChannels().get(i).getMembers().size() ; j++){
                    if(Messanger.getChannels().get(i).getMembers().get(j).getId().equals(currentUser.getId())){
                        return "You're already a member of this channel!";
                    }
                }
                Messanger.getChannels().get(i).addMember(currentUser);
                currentUser.addChannel(Messanger.getChannels().get(i));
                currentUser.addChat(Messanger.getChannels().get(i));
                return "You have successfully joined the channel!";
            }
        }
        return "No channel with this id exists!";
    }
    String showAllchannelregex = "^show all channels$";
    Pattern showallchannelsreg = Pattern.compile(showAllchannelregex);
    String createNewchannelregex = "^create\\snew\\schannel\\si\\s(?<id>[^\\s]+)\\sn\\s(?<name>[^\\s]+)$";
    Pattern creatnewchannelreg = Pattern.compile(createNewchannelregex);
    String createNewgroupregex = "^create\\snew\\sgroup\\si\\s(?<id>[^\\s]+)\\sn\\s(?<name>[^\\s]+)$";
    Pattern creatnewgroupreg = Pattern.compile(createNewgroupregex);
    String joinchannelregex = "^join\\schannel\\si\\s(?<id>[^\\s]+)$";
    Pattern joinchannelreg = Pattern.compile(joinchannelregex);
    String createpvregex = "^start\\sa\\snew\\sprivate\\schat\\swith\\si\\s(?<id>[^\\s]+)$";
    Pattern createpvreg = Pattern.compile(createpvregex);
    String enterchatregex = "^enter\\s(?<chattype>(private\\schat|channel|group))\\si\\s(?<id>[^\\s]+)$";
    Pattern enterchatreg = Pattern.compile(enterchatregex);
    String showmychatsregex = "^show\\smy\\schats$";
    Pattern showmychatsreg = Pattern.compile(showmychatsregex);


}
