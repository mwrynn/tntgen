# Tunnels & Trolls Character Generator
Designed to roll up many different characters, using different rulesets. Output can be used for statistical analysis. Currently only outputs combat adds data.

Written in Java 1.8, but not sure offhand which other version it's compatible with. I suspect the newest feature used is ThreadLocalRandom, which was new in JDK 7.

To execute:

`java mwrynn.tnt.TntGen <number of characters to roll>`

For example, to roll 1000 characters per each kindred, per each set of rules:

`java mwrynn.tnt.TntGen 1000`
