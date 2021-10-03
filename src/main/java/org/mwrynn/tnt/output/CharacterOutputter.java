package org.mwrynn.tnt.output;

import org.mwrynn.tnt.character.Character;
import org.mwrynn.tnt.stat.Stat;
import org.mwrynn.tnt.stat.StatNames;

import java.util.Iterator;
import java.util.List;

public class CharacterOutputter implements Outputter {
    List<Character> characterList;
    boolean hasHeader;
    String delimiter;

    public CharacterOutputter(List<Character> characterList) {
        this.characterList = characterList;
    }

    public void output(boolean hasHeader, String delimiter) {
        this.hasHeader = hasHeader;
        this.delimiter = delimiter;

        //print as columns each attribute (STR, DEX, ..., include adds?)
        if(hasHeader) {
            String statHeaderPart = "";
            for (String statName : StatNames.statNames) {
                statHeaderPart = statHeaderPart + statName + delimiter;
            }

            //strip last delimiter
            statHeaderPart = statHeaderPart.substring(0, statHeaderPart.length() - delimiter.length());

            System.out.println("EDITION" + delimiter + "OPTIONALRULES" + delimiter + "KIN" + delimiter + statHeaderPart);
        }

        for (Character c : characterList)  {
            //print edition/optionalrules/kin
            System.out.print(c.getRulesEdition() + delimiter + c.getOptionalRules() + delimiter + c.getKinDef() + delimiter);

            List<Stat> stats = c.getStats();

            for (Iterator<Stat> it = stats.iterator(); it.hasNext();) {
                System.out.print(it.next().getValue());
                if (it.hasNext()) {
                    System.out.print(delimiter);
                }
            }

            System.out.println("");
        }
    }
}
