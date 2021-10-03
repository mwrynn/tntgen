package org.mwrynn.tnt.stat;

import org.mwrynn.tnt.RulesPlusKin;
import org.mwrynn.tnt.character.Character;
import org.mwrynn.tnt.stat.StatsMap.StatsMapKey;

import java.util.List;

import static org.mwrynn.tnt.character.attribute.AttributeName.*;

public class StatsCollector {
    private StatsMap statsMap;
    private List<String> statsNames;

    public StatsCollector(StatsMap statsMap, List<String> statsNames) {
        this.statsMap = statsMap;
        this.statsNames = statsNames;
    }

    public void collectAllStats(Character c, RulesPlusKin rulesPlusKin) {
        for(String stat : statsNames) {
            StatsMapKey statsMapKey = null;
            switch (stat) {
                case "STR":
                    statsMapKey = new StatsMapKey(rulesPlusKin, new Stat(STR.toString(), c.getStr().getValue()));
                    break;

                case "DEX":
                    statsMapKey = new StatsMapKey(rulesPlusKin, new Stat(DEX.toString(), c.getDex().getValue()));
                    break;

                case "CON":
                    statsMapKey = new StatsMapKey(rulesPlusKin, new Stat(CON.toString(), c.getCon().getValue()));
                    break;

                case "SPD":
                    statsMapKey = new StatsMapKey(rulesPlusKin, new Stat(SPD.toString(), c.getSpd().getValue()));
                    break;

                case "IQ":
                    statsMapKey = new StatsMapKey(rulesPlusKin, new Stat(IQ.toString(), c.getIq().getValue()));
                    break;

                case "LK":
                    statsMapKey = new StatsMapKey(rulesPlusKin, new Stat(LK.toString(), c.getLk().getValue()));
                    break;

                case "CHR":
                    statsMapKey = new StatsMapKey(rulesPlusKin, new Stat(CHR.toString(), c.getChr().getValue()));
                    break;

                case "WIZ":
                    statsMapKey = new StatsMapKey(rulesPlusKin, new Stat(WIZ.toString(), c.getWiz().getValue()));
                    break;

                case StatNames.ADDS:
                    statsMapKey = new StatsMapKey(rulesPlusKin, new Stat(StatNames.ADDS, c.getAdds()));
                    break;

            }

            Long tally = statsMap.get(statsMapKey);

            if (tally != null) {
                tally++;
            } else {
                tally = 1L;
            }

            statsMap.put(statsMapKey, tally);

        }
    }
}
