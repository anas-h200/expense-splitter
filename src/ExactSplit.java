import java.util.ArrayList;
import java.util.List;

public class ExactSplit implements SplitStrategy {
    private final List<Long> exactShares;

    public ExactSplit(List<Long> exactShares) {
        this.exactShares = exactShares;
    }

    @Override
    public List<Long> calculateShares(long totalAmount, List<User> participants) {
        int n = participants.size();
        List<Long> shares = new ArrayList<>();

        for (int i=0; i<n; i++) {
            shares.add(exactShares.get(i));
        }

        return shares;
    }
}
