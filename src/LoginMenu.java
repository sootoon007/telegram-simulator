import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    private String command;
    private User loggedin;
    public void run(Scanner scanner){
        while(true){
            command = scanner.nextLine();
            if(Pattern.matches(registerregex , command ) == true){
                matcher = registerreg.matcher(command);
                System.out.println(register(matcher));
            }
            else if(Pattern.matches(loginregex , command) == true){
                matcher = loginreg.matcher(command);
                String out = login(matcher);
                System.out.println(out);
                if(out.equals("User successfully logged in!")){

                    MessangerMenu ali = new MessangerMenu(loggedin);
                    ali.run(scanner);
                }
            }
            else if(command.equals("exit")){
                System.exit(0);
            }
            else{
                System.out.println("Invalid command!");
            }
        }
    }
    private String login(Matcher matcher){
        matcher.find();
        String id = matcher.group("id");
        String password = matcher.group("password");
        int existenc = -1;
        for(int i = 0 ; i < Messanger.getUsers().size() ; i++){
            if(Messanger.getUsers().get(i).getId().equals(id) == true){
                existenc = i ;
                break;
            }
        }
        if(existenc == -1){
            return "No user with this id exists!";
        }
        else if(!Messanger.getUsers().get(existenc).getPassword().equals(password)){
            return "Incorrect password!";
        }
        else{
            loggedin = Messanger.getUsers().get(existenc);
            return "User successfully logged in!";
        }
    }
    private String register(Matcher matcher){
        matcher.find();
        String password = matcher.group("password");
        String username = matcher.group("username");
        String id = matcher.group("id");

        if(Pattern.matches(usernameregex , username) == false){
            return "Username's format is invalid!";
        }
        if(Pattern.matches(passwordregex , password) == false){
            return "Password is weak!";
        }
        for(int i = 0 ; i < Messanger.getUsers().size() ; i++){
            if(Messanger.getUsers().get(i).getId().equals(id)){
                return "A user with this ID already exists!";
            }
        }
        User temp = new User(id , username , password);
        Messanger.addUser(temp);

        return "User has been created successfully!";
    }
    String registerregex = "^register\\si\\s(?<id>[^\\s]+)\\su\\s(?<username>[^\\s]+)\\sp\\s(?<password>[^\\s]+)\\s*$";
    Pattern registerreg = Pattern.compile(registerregex);
    String loginregex = "^login\\si\\s(?<id>[^\\s]+)\\sp\\s(?<password>[^\\s]+)\\s*$";
    Pattern loginreg = Pattern.compile(loginregex);
    String passwordregex = "^(?=(.*[0-9]){1,})(?=(.*[a-z]){1,})(?=(.*[A-Z]){1,})(?=(.*[\\*\\.\\!\\@\\$\\%\\^\\&\\(\\)\\{\\}\\[\\]\\:\\;\\<\\>\\,\\?\\/\\~\\_\\=\\-\\+\\|]){1,}).{8,32}$";
    Pattern passwordreg = Pattern.compile(passwordregex);
    String usernameregex = "^[a-zA-Z0-9\\_]+$";
    Pattern usernamereg = Pattern.compile(usernameregex);
    Matcher matcher ;
}
