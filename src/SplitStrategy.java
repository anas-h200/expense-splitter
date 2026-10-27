import java.util.List;

public interface SplitStrategy {
    List<Long> calculateShares(long totalAmount, List<User> participants);
}
