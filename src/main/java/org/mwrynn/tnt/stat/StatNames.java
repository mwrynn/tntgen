package org.mwrynn.tnt.stat;

import org.mwrynn.tnt.character.attribute.AttributeName;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatNames {
    public static final String ADDS = "ADDS";

    private static Set<String> statNames = Stream.of(AttributeName.values())
            .map(AttributeName::name)
            .collect(Collectors.toSet());

    public StatNames() {
        statNames.add(ADDS);
    }

    public boolean contains(String val) {
        return statNames.contains(val);
    }

}
