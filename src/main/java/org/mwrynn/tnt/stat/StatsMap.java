package org.mwrynn.tnt.stat;

import org.mwrynn.tnt.RulesPlusKin;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class StatsMap extends ConcurrentHashMap<StatsMap.StatsMapKey, Long> {

    public static class StatsMapKey implements Comparable<StatsMapKey> {
        public RulesPlusKin rulesPlusKin;
        public Stat stat;

        StatsMapKey(RulesPlusKin rulesPlusKin, Stat stat) {
            this.rulesPlusKin = rulesPlusKin;
            this.stat = stat;
        }

        @Override
        public int hashCode() {
            return Objects.hash(stat, rulesPlusKin);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            StatsMapKey other = (StatsMapKey)obj;

            return  (this.rulesPlusKin.equals(other.rulesPlusKin)) &&
                    (this.stat.equals(other.stat));
        }

        @Override
        public int compareTo(StatsMapKey other) {
            if(this.equals(other)) {
                return 0;
            }
            if(this.rulesPlusKin.equals(other.rulesPlusKin)) {
                return this.stat.compareTo(other.stat);
            }

            return this.rulesPlusKin.compareTo(other.rulesPlusKin);
        }
    }
}
