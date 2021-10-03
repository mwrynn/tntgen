package org.mwrynn.tnt.character;

import org.mwrynn.tnt.character.attribute.Attribute;
import org.mwrynn.tnt.character.attribute.AttributeName;
import org.mwrynn.tnt.rules.OptionalRules;
import org.mwrynn.tnt.rules.RulesEdition;
import org.mwrynn.tnt.stat.Stat;

import java.util.LinkedList;
import java.util.List;

import static org.mwrynn.tnt.character.attribute.AttributeName.*;
import static org.mwrynn.tnt.stat.StatNames.ADDS;

public class Character {
    protected Attribute str;
    protected Attribute dex;
    protected Attribute con;
    protected Attribute spd;

    protected Attribute iq;
    protected Attribute lk;
    protected Attribute chr;
    protected Attribute wiz;

    private RulesEdition rulesEdition = RulesEdition.DELUXE; //default rulesEdition is DELUXE
    private OptionalRules optionalRules;

    private KinDef kinDef;

    public Character(RulesEdition rulesEdition, OptionalRules optionalRules) {
        this.rulesEdition = rulesEdition;
        this.optionalRules = optionalRules;
        this.str = new Attribute(rulesEdition, STR, 0, 1.0f);
        this.dex = new Attribute(rulesEdition, DEX, 0, 1.0f);
        this.con = new Attribute(rulesEdition, CON, 0, 1.0f);
        this.spd = new Attribute(rulesEdition, AttributeName.SPD, 0, 1.0f);
        this.iq = new Attribute(rulesEdition, AttributeName.IQ, 0, 1.0f);
        this.lk = new Attribute(rulesEdition, AttributeName.LK, 0, 1.0f);
        this.chr = new Attribute(rulesEdition, AttributeName.CHR, 0, 1.0f);
        this.wiz = new Attribute(rulesEdition, AttributeName.WIZ, 0, 1.0f);
    }

    public int getAdds() {
        if (rulesEdition == RulesEdition.DELUXE) {
            return Math.max(str.getValue(), 12)-12 + Math.max(dex.getValue(), 12)-12 + Math.max(lk.getValue(), 12)-12 + Math.max(spd.getValue(), 12)-12;
        } else { //FIFTH
            return ( Math.max(str.getValue(), 12)-12 + Math.max(dex.getValue(), 12)-12 + Math.max(lk.getValue(), 12)-12 ) //positive adds
            - ( 9-Math.min(str.getValue(), 9) + 9-Math.min(dex.getValue(), 9) + 9-Math.min(lk.getValue(), 9)); //negative adds
        }
    }

    public Attribute getStr() {
        return str;
    }

    public void setStr(Attribute str) {
        this.str = str;
    }

    public void setStr(int unmultipliedStr) {
        str.setValue(unmultipliedStr);
    }

    public Attribute getDex() {
        return dex;
    }

    public void setDex(int unmultipliedDex) {
        dex.setValue(unmultipliedDex);
    }

    public void setDex(Attribute dex) {
        this.dex = dex;
    }

    public Attribute getCon() {
        return con;
    }

    public void setCon(int unmultipliedCon) {
        con.setValue(unmultipliedCon);
    }

    public void setCon(Attribute con) {
        this.con = con;
    }

    public Attribute getSpd() {
        return spd;
    }

    public void setSpd(int unmultipliedSpd) {
        spd.setValue(unmultipliedSpd);
    }

    public void setSpd(Attribute spd) {
        this.spd = spd;
    }

    public Attribute getIq() {
        return iq;
    }

    public void setIq(int unmultipliedIq) {
        iq.setValue(unmultipliedIq);
    }

    public void setIq(Attribute iq) {
        this.iq = iq;
    }

    public Attribute getLk() {
        return lk;
    }

    public void setLk(int unmultipliedLk) {
        lk.setValue(unmultipliedLk);
    }

    public void setLk(Attribute lk) {
        this.lk = lk;
    }

    public Attribute getChr() {
        return chr;
    }

    public void setChr(int unmultipliedChr) {
        chr.setValue(unmultipliedChr);
    }

    public void setChr(Attribute chr) {
        this.chr = chr;
    }

    public Attribute getWiz() {
        return wiz;
    }

    public void setWiz(int unmultipliedWiz) {
        wiz.setValue(unmultipliedWiz);
    }

    public void setWiz(Attribute wiz) {
        this.wiz = wiz;
    }

    public KinDef getKinDef() {
        return kinDef;
    }

    public void setRulesEdition(RulesEdition rulesEdition) {
        this.rulesEdition = rulesEdition;
    }

    public RulesEdition getRulesEdition() {
        return rulesEdition;
    }

    public void setOptionalRules(OptionalRules optionalRules) {
        this.optionalRules = optionalRules;
    }

    public OptionalRules getOptionalRules() {
        return optionalRules;
    }


    public void setKinDef(KinDef kinDef) {
        this.kinDef = kinDef;
        this.str.setMultiplier(kinDef.getStrMult());
        this.dex.setMultiplier(kinDef.getDexMult());
        this.con.setMultiplier(kinDef.getConMult());
        this.spd.setMultiplier(kinDef.getSpdMult());
        this.iq.setMultiplier(kinDef.getIqMult());
        this.lk.setMultiplier(kinDef.getLkMult());
        this.chr.setMultiplier(kinDef.getChrMult());
        this.wiz.setMultiplier(kinDef.getWizMult());
    }

    public List<Attribute> getAttributes() {
        List<Attribute> attributes = new LinkedList<>();
        attributes.add(this.str);
        attributes.add(this.dex);
        attributes.add(this.con);
        attributes.add(this.spd);
        attributes.add(this.iq);
        attributes.add(this.lk);
        attributes.add(this.chr);
        attributes.add(this.wiz);

        return attributes;
    }

    public List<Stat> getStats() {
        List<Stat> stats = new LinkedList<>();
        stats.add(new Stat(STR.toString(), this.getStr().getValue()));
        stats.add(new Stat(DEX.toString(), this.getDex().getValue()));
        stats.add(new Stat(CON.toString(), this.getCon().getValue()));
        stats.add(new Stat(SPD.toString(), this.getSpd().getValue()));
        stats.add(new Stat(IQ.toString(), this.getIq().getValue()));
        stats.add(new Stat(LK.toString(), this.getLk().getValue()));
        stats.add(new Stat(CHR.toString(), this.getChr().getValue()));
        stats.add(new Stat(SPD.toString(), this.getSpd().getValue()));
        stats.add(new Stat(ADDS, this.getAdds()));

        return stats;
    }
}
