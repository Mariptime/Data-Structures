public class PhoneBook implements IMap {
    private final Entry[] data;
    private int size = 0;

    public PhoneBook(int size) {data = new Entry[size]; }

    public PhoneBook()         {this(10); }

    @Override
    public PhoneNumber put(Person key, PhoneNumber value) {
        if (size >= data.length) {
            Entry[] data2 = new Entry[data.length * 2];
            System.arraycopy(data, 0, data2, 0, data.length * 2);
            System.arraycopy(data2, 0, data, 0, data2.length);
        }
        int index = MyHash(key.toString());
        if (data[index] == null) {
            data[index] = new Entry(key, value);
            size++;
            return null;
        } else if (data[index].person == key) {
            PhoneNumber temp = data[index].phone;
            data[index] = new Entry(key, value);
            size++;
            return temp;
        } else {
            while (data[index] != null && (!data[index].equals(new Entry(key, null)) && !(data[index].person == key))) {
                index = (index + 1) % data.length;
            }
            data[index].phone = value;
            return null;
        }
    }

    private int MyHash(String S) {
        int Multi = 20;
        int Rem = data.length % Multi;
        if (Rem == 0 || Rem == 1 || Rem == data.length - 1) Multi = 40;
        int Sum = 0;
        for (int i = 0; i < S.length(); i++)
            Sum = (Multi * Sum + (int) S.charAt(i)) % data.length;
        return Sum;
    }

    @Override
    public PhoneNumber get(Person Key) {
        int Index = MyHash(Key.toString());
        Entry e = FindNode(Key,Index);
        if (e==null) return null;
        else return e.phone;
    }
    private Entry FindNode(Person p, int Index) {
        Entry e = data[Index];
        while (e != null && !(e.person.toString().equals(p.toString())))
            e = e.next;
        return e;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public PhoneNumber remove(Person person) {
        Entry e = FindNode(person,MyHash(person.toString()));
        PhoneNumber output = null;
        if (e!=null) {
            output = e.phone;
            e.clear();
        }
        return output;
    }
}
class Entry {
    Person person;
    PhoneNumber phone;
    Entry next;

    public Entry(Person person, PhoneNumber number) {
        this.person = person;
        this.phone = number;
    }

    public void clear()
    {
        person = null;
        phone = null;
    }

    public String toString() {
        return person + " - " + phone;
    }
}
