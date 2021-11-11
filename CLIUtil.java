import java.util.Scanner;

public class CLIUtil {
    static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">> ");
        String str = scanner.nextLine();
        str.replace("\n", "");
        if (!str.trim().isEmpty() && str != "") {
            System.out.println("string isn't null or only white spaces!");
            scanner.close();
            return str;
        } else {
            System.out.println("Invalid command.");
        }
        scanner.close();
        return str;


    }
}
