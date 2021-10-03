package org.mwrynn.tnt.character.attribute;

public enum AttributeName {
    STR {
        public String toString () {
            return "STR";
        }
        public int sortOrder() {
            return 1;
        }
    },
    DEX {
        public String toString () {
            return "DEX";
        }
        public int sortOrder() {
            return 2;
        }
    },
    CON {
        public String toString () {
            return "CON";
        }
        public int sortOrder() {
            return 3;
        }
    },
    SPD {
        public String toString () {
            return "SPD";
        }
        public int sortOrder() {
            return 4;
        }
    },
    IQ {
        public String toString () {
            return "IQ";
        }
        public int sortOrder() {
            return 5;
        }
    },
    LK {
        public String toString () {
            return "LK";
        }
        public int sortOrder() {
            return 6;
        }
    },
    CHR {
        public String toString () {
            return "CHR";
        }
        public int sortOrder() {
            return 7;
        }
    },
    WIZ {
        public String toString () {
            return "WIZ";
        }
        public int sortOrder() {
            return 8;
        }
    }
}
