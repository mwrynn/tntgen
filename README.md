# Tunnels & Trolls Character Generator
Designed to roll up many different characters, using different rules sets. Output can be used for statistical analysis. Currently only outputs combat adds data.

Written in Java 1.8, but not sure offhand which other version it's compatible with. I suspect the newest feature used is ThreadLocalRandom, which was new in JDK 7.

usage: tntgen
 -r,--rolls <arg>      number of rolls per combination of rules and kindred
 -p,--parallel <arg>   number of parallel threads
 -t,--time             display execution time
