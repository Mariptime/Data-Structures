import java.util.*;

public class PhoneNumber {
    private final int number;

    public PhoneNumber(int num) {
        this.number = num;
    }

    public String toString() {
        return "" + number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PhoneNumber))
            return false;
        PhoneNumber that = (PhoneNumber) obj;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
