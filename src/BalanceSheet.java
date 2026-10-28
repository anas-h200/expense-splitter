import java.util.HashMap;
import java.util.Map;

public class BalanceSheet {
    private final Map<User, Long> balances = new HashMap<>();

    public void adjust(User user, long delta) {
        long currentBalance = balances.getOrDefault(user, 0L);
        currentBalance += delta;
        balances.put(user, currentBalance);
    }

    public long getBalance(User user) {
        return balances.getOrDefault(user, 0L);
    }
}
