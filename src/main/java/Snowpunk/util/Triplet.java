package Snowpunk.util;

import java.util.Objects;

public class Triplet<K, F, V> {
    private final K key;
    private final F flag;
    private final V value;

    public Triplet(K key, F flag, V value) {
        this.key = key;
        this.flag = flag;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public F getFlag() {
        return this.flag;
    }

    public V getValue() {
        return this.value;
    }

    public String toString() {
        return this.key + "=" + this.flag + "+" + this.value;
    }

    public boolean equals(Object o) {
        if (o instanceof Triplet) {
            Triplet<?, ?, ?> other = (Triplet<?, ?, ?>) o;
            return Objects.equals(this.key, other.key) && Objects.equals(this.flag, other.flag) && Objects.equals(this.value, other.value);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.key, this.flag, this.value);
    }
}
