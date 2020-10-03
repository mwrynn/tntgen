package mwrynn.tnt;

import mwrynn.tnt.character.Character;
import mwrynn.tnt.dice.Dice;
import mwrynn.tnt.roller.Roller;
import mwrynn.tnt.rules.RulesSet;
import mwrynn.tnt.character.*;

import java.util.HashMap;

public class TntGen {
    private int numRollsEach;
    HashMap<MapKey, Long> addsMap = new HashMap<>();

    static class MapKey {
        RulesSet rulesSet;
        String kindred;
        int addsValue;

        MapKey(RulesSet rulesSet, String kindred, int addsValue) {
            this.rulesSet = rulesSet;
            this.kindred = kindred;
            this.addsValue = addsValue;
        }

        @Override
        public int hashCode()
        {
            int result = (addsValue ^ (addsValue >>> 16));
            result = 15 * result + (rulesSet != null ? rulesSet.hashCode() : 0);
            result = 15 * result + (kindred != null ? kindred.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            if (!super.equals(obj)) return false;

            MapKey other = (MapKey)obj;

            return  (this.rulesSet.equals(other.rulesSet)) &&
                    (this.kindred.equals(other.kindred)) &&
                    (this.addsValue == other.addsValue);
        }

        public String toString() {
            return rulesSet + "," + kindred + "," + addsValue;
        }
    }

    public void generate(int numRollsEach) {
        this.numRollsEach = numRollsEach;
        //need mapping of RulesSet + String for Kindred + AddsValue to Long (for the tally)

        //would be better to iterate of all kindreds
        for (RulesSet rulesSet : RulesSet.values()) {
            Dwarf dwarf = new Dwarf(rulesSet);
            generateLoop(dwarf);

            Elf elf = new Elf(rulesSet);
            generateLoop(elf);

            Fairy fairy = new Fairy(rulesSet);
            generateLoop(fairy);

            GristleGrim gristleGrim = new GristleGrim(rulesSet);
            generateLoop(gristleGrim);

            Hobb hobb = new Hobb(rulesSet);
            generateLoop(hobb);

            Human human = new Human(rulesSet);
            generateLoop(human);

            Leprechaun leprechaun = new Leprechaun(rulesSet);
            generateLoop(leprechaun);

            Midgardian midgardian = new Midgardian(rulesSet);
            generateLoop(midgardian);

        }


        printResults();
    }

    private void printResults() {
        addsMap.forEach((key,value) -> System.out.println(key + "," + value));
    }

    private void generateLoop(Character c) {
        Roller roller;
        Dice dice = new Dice(3, 6);

        if (!c.isValidInRulesSet()) {
            return;
        }

        for (int i = 0; i < numRollsEach; i++) {
            roller = new Roller(c.rulesSet, c, dice);
            roller.rollAllAttributes();
            MapKey mapKey = new MapKey(c.rulesSet, c.kinName(), c.getAdds());
            Long existingAddsTally = addsMap.get(mapKey);

            if (existingAddsTally != null) {
                existingAddsTally++;
            } else {
                existingAddsTally=1L;
            }

            addsMap.put(mapKey, existingAddsTally);
        }
    }

    public static void main(String[] args) {
        int numRollsEach = Integer.parseInt(args[0]);
        TntGen tntGen = new TntGen();
        tntGen.generate(numRollsEach);
    }
}
