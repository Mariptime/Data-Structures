public class MyHashTable<K,V>  {
    private final Entry<K,V>[] data;
    private int size = 0;

    public MyHashTable(int size) {data = new Entry[size]; }

    public MyHashTable()         {this(10); }

    public V put(K key, V value) {
        if (size >= data.length) {
            Entry<K,V>[] data2 = new Entry[data.length * 2];
            System.arraycopy(data, 0, data2, 0, data.length * 2);
            System.arraycopy(data2, 0, data, 0, data2.length);
        }
        int index = MyHash(key.toString());
        if (data[index] == null) {
            data[index] = new Entry(key, value);
            size++;
            return null;
        } else if (data[index].key == key) {
            V temp = data[index].value;
            data[index] = new Entry(key, value);
            size++;
            return temp;
        } else {
            while (data[index] != null && (!data[index].equals(new Entry(key, null)) && !(data[index].key == key))) {
                index = (index + 1) % data.length;
            }
            data[index].value = value;
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

    public V get(K Key) {
        int Index = MyHash(Key.toString());
        Entry e = FindNode(Key,Index);
        if (e==null) return null;
        else return (V) e.value;
    }
    private Entry FindNode(K key, int i) {
        Entry e = data[i];
        while (e != null && !(e.key.toString().equals(key.toString())))
            e = e.next;
        return e;
    }

    public int size() {
        return size;
    }

    public V remove(K key) {
        Entry e = FindNode(key,MyHash(key.toString()));
        V output = null;
        if (e!=null) {
            output = (V) e.value;
            e.clear();
        }
        return output;
    }

    private class Entry<K,V> {
        K key;
        V value;
        Entry<K,V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void clear()
        {
            key = null;
            value = null;
        }

        public String toString() {
            return key + " - " + value;
        }
    }
}
