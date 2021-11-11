import java.util.Scanner;

public class CLIUtil {
    static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[0;4m>>\033[0;0m ");
        String str = scanner.nextLine();
        str.replace("\n", "");
        if (!str.trim().isEmpty() && str != "") {
            // System.out.println("string isn't null or only white spaces!");
            // scanner.close();
            // return str;
        } else {
            System.out.println("Invalid command.");
            CLIUtil.getUserInput();
        }
        // scanner.close();
        return str;


    }
}
