import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BalanceSheetTest {
    @Test
    void equalSplitUpdatesBalances() {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");
        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense dinner = new Expense(1, "Dinner", 30000, u1, SplitType.EQUAL);
        sheet.addExpense(dinner, users, new EqualSplit());

        assertEquals(20000, sheet.getBalance(u1));
        assertEquals(-10000, sheet.getBalance(u2));
        assertTrue(sheet.isBalanced());
    }

    @Test
    void percentageSplitUpdatesBalances() {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");
        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense cab = new Expense(1, "Cab", 15000, u2, SplitType.PERCENTAGE);
        sheet.addExpense(cab, users, new PercentageSplit(List.of(50, 30, 20)));

        assertEquals(-7500, sheet.getBalance(u1));
        assertEquals(10500, sheet.getBalance(u2));
        assertEquals(-3000, sheet.getBalance(u3));
        assertTrue(sheet.isBalanced());
    }

    @Test
    void exactSplitUpdatesBalances() {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");
        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense car = new Expense(1, "Car", 6000, u2, SplitType.EXACT);
        sheet.addExpense(car, users, new ExactSplit(List.of(1000L, 2000L, 3000L)));

        assertEquals(-1000L, sheet.getBalance(u1));
        assertEquals(4000L, sheet.getBalance(u2));
        assertEquals(-3000L, sheet.getBalance(u3));
        assertTrue(sheet.isBalanced());
    }

    @Test
    void zeroAmountIsRejected() {
        User u1 = new User(1, "U1", "u1@abc.com");
        List<User> users = List.of(u1);

        BalanceSheet sheet = new BalanceSheet();
        Expense bad = new Expense(1, "Bad", 0, u1, SplitType.EQUAL);

        assertThrows(InvalidExpenseException.class, () -> sheet.addExpense(bad, users, new EqualSplit()));
    }

    @Test
    void emptyParticipantsIsRejected() {
        User u1 = new User(1, "U1", "u1@abc.com");
        List<User> users = List.of();

        BalanceSheet sheet = new BalanceSheet();
        Expense noOne = new Expense(1, "NoOne", 1000, u1, SplitType.EQUAL);

        assertThrows(InvalidExpenseException.class, () -> sheet.addExpense(noOne, users, new EqualSplit()));
    }

    @Test
    void percentagesNotSummingTo100AreRejected() {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");
        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense badPct = new Expense(1, "BadPct", 2000, u1, SplitType.PERCENTAGE);
        assertThrows(InvalidSplitException.class, () -> sheet.addExpense(badPct, users, new PercentageSplit(List.of(50, 30, 30))));
    }

    @Test
    void percentageCountMismatchIsRejected() {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");
        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense differCount = new Expense(1, "DifferCount", 3000, u1, SplitType.PERCENTAGE);
        assertThrows(InvalidSplitException.class, () -> sheet.addExpense(differCount, users, new PercentageSplit(List.of(50, 50))));
    }

    @Test
    void exactSharesNotSummingToTotalAreRejected() {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");
        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense notExact = new Expense(1, "NotExact", 1000, u1, SplitType.EXACT);
        assertThrows(InvalidSplitException.class, () -> sheet.addExpense(notExact, users, new ExactSplit(List.of(300L, 300L, 300L))));
    }

    @Test
    void exactCountMismatchIsRejected() {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");
        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense countMismatch = new Expense(1, "CountMismatch", 1000, u1, SplitType.EXACT);
        assertThrows(InvalidSplitException.class, () -> sheet.addExpense(countMismatch, users, new ExactSplit(List.of(500L, 500L))));
    }

    @Test
    void settlementsPayTheCreditor() {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");
        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense dinner = new Expense(1, "Dinner", 30000, u1, SplitType.EQUAL);
        sheet.addExpense(dinner, users, new EqualSplit());

        List<Settlement> settlements = sheet.getSettlements();

        assertEquals(2, settlements.size());
        for (Settlement s: settlements) {
            assertEquals(10000, s.getAmount());
            assertEquals(u1, s.getTo());
        }
    }

    @Test
    void settlementsDoNotChangeBalances() {
        User u1 = new User(1, "U1", "u1@abc.com");
        User u2 = new User(2, "U2", "u2@abc.com");
        User u3 = new User(3, "U3", "u3@abc.com");
        List<User> users = List.of(u1, u2, u3);

        BalanceSheet sheet = new BalanceSheet();
        Expense dinner = new Expense(1, "Dinner", 30000, u1, SplitType.EQUAL);
        sheet.addExpense(dinner, users, new EqualSplit());

        List<Settlement> settlements = sheet.getSettlements();

        assertEquals(20000, sheet.getBalance(u1));
        assertEquals(-10000, sheet.getBalance(u2));
        assertEquals(-10000, sheet.getBalance(u3));
    }

}
