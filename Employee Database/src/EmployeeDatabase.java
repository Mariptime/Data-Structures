import java.math.*;

public class EmployeeDatabase {
    private final Entry[] data;
    private int size = 0;

    public EmployeeDatabase(int initCapacity) {
        int prime = new BigInteger("" + initCapacity).nextProbablePrime().intValueExact();
        data = new Entry[prime];
    }

    public int hashFunction(int key) {

        int sum = 0;
        while (key > 0) {
            sum = key % 10;
            key /= 10;
        }
        return sum % data.length;
    }

    public PhoneNumber put(int id, PhoneNumber employee) {
        if (size == data.length)
            throw new IllegalStateException("Table is full!");

        int index = hashFunction(id);
        if (data[index] == null) {
            data[index] = new Entry(id, employee);
            size++;
            return null;
        } else if (data[index].ID == id) {
            PhoneNumber temp = data[index].employee;
            data[index] = new Entry(id, employee);
            return temp;
        } else {
            while (data[index] != null && (!data[index].equals(new Entry(0, null)) && !(data[index].ID == id))) {
                index = (index + 1) % data.length;
            }
            data[index].employee = employee;
            return null;
        }
    }

    public PhoneNumber get(int id) {
        int index = hashFunction(id);
        while (data[index] != null && data[index].ID != id) {
            index = (index + 1) % data.length;
        }
        if (data[index] != null && data[index].ID == id)
            return data[index].employee;
        return null;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        String result = "Capacity: " + data.length + "\n";
        result += "Size: " + size + "\n";
        for (Entry e : data) {
            if (e != null) {
                result += e.ID + ": " + e.employee + "\n";
            }
        }
        return result;
    }
}

class Entry {
    int ID;
    PhoneNumber employee;

    public Entry(int ID, PhoneNumber employee) {
        this.ID = ID;
        this.employee = employee;
    }

    public String toString() {
        return ID + " - " + employee;
    }
}

class PhoneNumber {
    String name;

    public PhoneNumber(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}