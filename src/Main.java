import com.sen.fitness.center.Assistant;
import com.sen.fitness.center.Client;
import com.sen.fitness.data.Database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
//        Assistant assistant = new Assistant();
//        assistant.login("aleks", "password");
//        assistant.doMenuCommand(5);

        Client client = new Client();
        client.login("john", "password");
        client.doMenuCommand(5);
    }
}