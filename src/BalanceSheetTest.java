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
}
