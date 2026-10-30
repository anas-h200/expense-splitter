import java.util.List;

public class Main {
    public static void main(String[] args) {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");

        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense dinner = new Expense(1, "Dinner", 30000, u1, SplitType.EQUAL);
        sheet.addExpense(dinner, users, new EqualSplit());

        Expense cab = new Expense(2, "Cab", 15000, u2, SplitType.PERCENTAGE);
        sheet.addExpense(cab, users, new PercentageSplit(List.of(50,30,20)));

        Expense snacks = new Expense(3, "Snacks", 6000, u3, SplitType.EXACT);
        sheet.addExpense(snacks, users, new ExactSplit(List.of(1000L, 2000L, 3000L)));

        Expense bad = new Expense(4, "Bad", 0, u1, SplitType.EQUAL);
        try{
            sheet.addExpense(bad, users, new EqualSplit());
        } catch (InvalidExpenseException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        Expense noOne = new Expense(5, "NoOne", 1000, u1, SplitType.EQUAL);
        try {
            sheet.addExpense(noOne, List.of(), new EqualSplit());
        } catch (InvalidExpenseException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        Expense badPct = new Expense(6, "BadPct", 1000, u1, SplitType.PERCENTAGE);
        try {
            sheet.addExpense(badPct, users, new PercentageSplit(List.of(50,30,30)));
        } catch (InvalidSplitException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        Expense differCount = new Expense(7, "DifferCount", 3000, u2, SplitType.PERCENTAGE);
        try {
            sheet.addExpense(differCount, users, new PercentageSplit(List.of(50,50)));
        } catch (InvalidSplitException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        Expense notExact = new Expense(8, "NotExact", 5000, u3, SplitType.EXACT);
        try {
            sheet.addExpense(notExact, users, new ExactSplit(List.of(1000L,1500L,2000L)));
        } catch (InvalidSplitException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        Expense countMismatch = new Expense(9, "CountMismatch", 3500, u2, SplitType.EXACT);
        try {
            sheet.addExpense(countMismatch, users, new ExactSplit(List.of(1000L, 2000L)));
        } catch (InvalidSplitException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        sheet.printBalances();
        System.out.println("balanced: " + sheet.isBalanced());

        sheet.getSettlements();
    }
}