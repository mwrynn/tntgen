package org.mwrynn.tnt.character;

@Deprecated
public enum Kin {

    DWARF {
        public String toString() {
            return "Dwarf";
        }
        public Class getKinClass() {
            return Dwarf.class;
        }
    },

    ELF {
        public String toString () {
            return "Elf";
        }
        public Class getKinClass() {
            return Elf.class;
        }
    },

    FAIRY {
        public String toString () {
            return "Fairy";
        }

        public Class getKinClass() {
            return Fairy.class;
        }
    },

    GRISTLEGRIM {
        public String toString () {
            return "GristleGrim";
        }

        public Class getKinClass() {
            return GristleGrim.class;
        }
    },

    HOBB {
        public String toString () {
            return "Hobb";
        }

        public Class getKinClass() {
            return Hobb.class;
        }
    },

    HUMAN {
        public String toString () {
            return "Human";
        }

        public Class getKinClass() {
            return Human.class;
        }
    },

    LEPRECHAUN {
        public String toString () {
            return "Leprechaun";
        }

        public Class getKinClass() {
            return Leprechaun.class;
        }
    },

    MIDGARDIAN {
        public String toString () {
            return "Midgardian";
        }

        public Class getKinClass() {
            return Midgardian.class;
        }
    };

    public abstract Class getKinClass();

}