package org.mwrynn.tnt.stat;

import java.util.Objects;

public class Stat implements Comparable<Stat> {
    private String statName;
    private int value;

    public Stat(String statName, int value) {
        this.statName = statName;
        this.value = value;
    }

    public void setName(String statName) {
        this.statName = statName;
    }

    public String getStatName() {
        return statName;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int hashCode() {
        return Objects.hash(statName, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Stat other = (Stat)obj;

        return  (this.statName.equals(other.statName)) &&
                (this.value == other.value);
    }

    @Override
    public String toString() {
        return statName + "," + String.valueOf(value);
    }

    @Override
    public int compareTo(Stat other) {

        if (this.equals(other)) {
            return 0;
        }
        if (this.statName.equals(other.statName)) {
            return this.value < other.value ? -1 : 1;
        }
        return this.statName.compareTo(other.toString());
    }
}
