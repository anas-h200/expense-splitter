import java.util.ArrayList;
import java.util.List;

public class EqualSplit implements SplitStrategy {
    @Override
    public List<Long> calculateShares(long totalAmount, List<User> participants) {
        int n = participants.size();
        long base = totalAmount/n;
        long remainder = totalAmount%n;

        List<Long> shares = new ArrayList<>();

        for (int i=0; i<n; i++) {
            if (i < remainder) {
                shares.add(base + 1);
            }
            else {
                shares.add(base);
            }
        }

        return shares;
    }
}
