# Tunnels & Trolls Character Generator
This is a tool used for the Tunnels & Trolls, the second roleplaying game ever published, written by Ken St. Andrew and published by Flying Buffalo, Inc. This tool, tntgen, is designed to roll up a large number of character attributes of all different kindreds, using various rules sets. Output can be used for statistical analysis and comparisons can be made between rules sets, kindreds, etc. Currently only outputs combat adds data but more is to come.  
  
  
Written in Java 1.8, but not sure offhand which other version it's compatible with. I suspect the newest feature used is ThreadLocalRandom, which was new in JDK 7.  
  
usage: tntgen  
 -r,--rolls <arg>      number of rolls per combination of rules and kindred  
 -p,--parallel <arg>   number of parallel threads  
 -t,--time             display execution time  
  
Output format:  
```
\<edition\>,\<kindred\>,\<combat-adds\>,\<total\>  
```
  
For example:  
```
FIFTH,Hobb,3,8829
DELUXE_W_LOW_REROLL,Fairy,100,1
DELUXE,Midgardian,56,81
```

If the --time option is set, the final line of output will be the execution time:
```
execution time: <time-in-milliseconds> ms
```

For example:
```
execution time: 858 ms
```
