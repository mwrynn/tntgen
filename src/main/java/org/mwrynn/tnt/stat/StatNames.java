package org.mwrynn.tnt.stat;

import org.mwrynn.tnt.character.attribute.AttributeName;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatNames {
    public static final String ADDS = "ADDS";

    public static List<String> statNames = Stream.of(AttributeName.values())
            .map(AttributeName::name)
            .collect(Collectors.toList());

    static {
        statNames.add(ADDS);
    }

    public boolean contains(String val) {
        return statNames.contains(val);
    }

}
