package org.mwrynn.tnt.character;

import org.mwrynn.tnt.rules.RulesEdition;

import java.util.Objects;

public class KinDef implements Comparable<KinDef> {
    private String kinName;

    private RulesEdition rulesEdition;

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
    public KinDef(RulesEdition rulesEdition, String kinName, float strMult, float dexMult,
                  float conMult, float spdMult, float iqMult, float lkMult, float chrMult, float wizMult) {
        this.rulesEdition = rulesEdition;
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
        this.kinName = kinName.toUpperCase();
    }

    public RulesEdition getRulesEdition() {
        return rulesEdition;
    }

    public void setKinName(RulesEdition rulesEdition) {
        this.rulesEdition = rulesEdition;
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

    public void setRulesEdition(RulesEdition rulesEdition) {
        this.rulesEdition = rulesEdition;
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
                (this.getRulesEdition() == other.getRulesEdition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(kinName, rulesEdition);
    }

    @Override
    public int compareTo(KinDef other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.getRulesEdition().equals(other.getRulesEdition())) {
            return this.getKinName().compareTo(other.getKinName());
        }
        return this.getKinName().compareTo(other.getKinName());
    }

    @Override
    public String toString() {
        return kinName;
    }
}
