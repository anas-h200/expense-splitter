import java.util.list;

public interface SplitStrategy {
    List<Long> calculateShares(long totalAmount, List<User> participants);
}
