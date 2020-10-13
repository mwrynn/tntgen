package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesSet;
import org.mwrynn.tnt.stat.Stat;

import java.util.Objects;

public class KinDef implements Comparable<KinDef> {
    private String kinName;

    private RulesSet rulesSet;

    private float strMult = 1.0f;
    private float dexMult = 1.0f;
    private float conMult = 1.0f;
    private float spdMult = 1.0f;
    private float iqMult = 1.0f;
    private float lkMult = 1.0f;
    private float chrMult = 1.0f;
    private float wizMult = 1.0f;

    public KinDef() {

    }

    //could use Builder pattern
    public KinDef(RulesSet rulesSet, String kinName, float strMult, float dexMult,
                  float conMult, float spdMult, float iqMult, float lkMult, float chrMult, float wizMult) {
        this.rulesSet = rulesSet;
        this.kinName = kinName;
        this.strMult = strMult;
        this.dexMult = dexMult;
        this.conMult = conMult;
        this.spdMult = spdMult;
        this.iqMult = iqMult;
        this.lkMult = lkMult;
        this.chrMult = chrMult;
        this.wizMult = wizMult;
    }

    public String getKinName() {
        return kinName;
    }

    public void setKinName(String kinName) {
        this.kinName = kinName;
    }

    public RulesSet getRulesSet() {
        return rulesSet;
    }

    public void setKinName(RulesSet rulesSet) {
        this.rulesSet = rulesSet;
    }

    public float getStrMult() {
        return strMult;
    }

    public float getDexMult() {
        return dexMult;
    }

    public float getConMult() {
        return conMult;
    }

    public float getSpdMult() {
        return spdMult;
    }

    public float getIqMult() {
        return iqMult;
    }

    public float getLkMult() {
        return lkMult;
    }

    public float getChrMult() {
        return chrMult;
    }

    public float getWizMult() {
        return wizMult;
    }

    public void setRulesSet(RulesSet rulesSet) {
        this.rulesSet = rulesSet;
    }

    public void setStrMult(float strMult) {
        this.strMult = strMult;
    }

    public void setDexMult(float dexMult) {
        this.dexMult = dexMult;
    }

    public void setConMult(float conMult) {
        this.conMult = conMult;
    }

    public void setSpdMult(float spdMult) {
        this.spdMult = spdMult;
    }

    public void setIqMult(float iqMult) {
        this.iqMult = iqMult;
    }

    public void setLkMult(float lkMult) {
        this.lkMult = lkMult;
    }

    public void setChrMult(float chrMult) {
        this.chrMult = chrMult;
    }

    public void setWizMult(float wizMult) {
        this.wizMult = wizMult;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        KinDef other = (KinDef)obj;

        return  (this.getKinName().equals(other.getKinName())) &&
                (this.getRulesSet() == other.getRulesSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(kinName, rulesSet);
    }

    @Override
    public int compareTo(KinDef other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.getRulesSet().equals(other.getRulesSet())) {
            return this.getKinName().compareTo(other.getKinName());
        }
        return this.getKinName().compareTo(other.getKinName());
    }

}
