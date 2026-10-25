import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");

        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1, "Expense 1", 10000, u3, SplitType.EXACT));
        expenses.add(new Expense(2, "Expense 2", 15000, u1, SplitType.EQUAL));
        expenses.add(new Expense(3, "Expense 3", 17000, u2, SplitType.PERCENTAGE));
        expenses.add(new Expense(4, "Expense 4", 20000, u1, SplitType.EXACT));

        for (Expense e : expenses) {
            System.out.println(e);
        }
    }
}