# Tunnels & Trolls Character Statistics Generator
This is a tool used for Tunnels & Trolls, the second roleplaying game ever published, designed and written by Ken St. Andre, et. al, and published by Flying Buffalo, Inc. This tool, tntgen, is designed to roll up a large number of character attributes of all different kindreds, using various rules sets. Output can be used for statistical analysis and comparisons can be made between rules sets, kindreds, etc.   
  
  
Written in Java 1.8, but not sure offhand which other version it's compatible with. I suspect the newest feature used is ThreadLocalRandom, which was new in JDK 7.

For now to run this program you must build it for yourself. This may be new to you if you're not a programmer, but for now I have not attached a build to this github repo, so that's what we've got. :) 

You need to have the JDK 8 or greater installed, and have git and maven installed. 

1. Clone this repo by going to https://github.com/mwrynn/tntgen/ and clicking the green "Code" button which will help you get set up.

2. Go the the tntgen directory in your command prompt/terminal.

3. Build by running the maven command: mvn package

4. This should output a file, named something like ./tntgen/target/tntgen-1.0-SNAPSHOT.jar. You will use this jar with your java command. (See below.)

Command line args are defined immediately below:

```
usage: tntgen
 -c,--char-output       whether to output individual character stats or
                        aggregated stats; default is false (aggregated)
 -d,--delimiter <arg>   output field delimiter; default is ,
 -e,--edition <arg>     comma-separated list of the rules editions to use. Valid editions at this time: FIFTH,DELUXE
 -h,--header            output column headers
 -k,--kin <arg>         comma-separated list of the kindred to collect
                        stats for. See kin_conf.yaml for valid kin.
 -o,--optional <arg>    comma-separated list of the optional rules to
                        apply. Valid optional rules at this time: NONE,LOW_REROLL
 -p,--parallel <arg>    number of parallel threads
 -r,--rolls <arg>       number of rolls per combination of rules and
                        kindred
 -s,--stats <arg>       comma-separated list of the stats to collect and
                        output; ignored if individual character output is enabled;
                        Valid stats: ADDS,STR,DEX,CON,SPD,IQ,LK,CHR,WIZ
 -t,--time              display execution time
```

Output format:  
```
<rules-set>,<optional-rules>,<kindred>,<stat-name>,<total>  
```
  
For example:  
```
FIFTH,NONE,Hobb,3,8829
DELUXE,NONE,Fairy,100,1
DELUXE,LOW_REROLL,Midgardian,56,81
```

If the --time option is set, the final line of output will be the execution time:
```
execution time: <time-in-milliseconds> ms
```

For example:
```
execution time: 858 ms
```

Full example of execution and output. Generate 10 rolls of each of every combination of kin HUMAN and DWARF, stats STR and ADDS, FIFTH edition only, do not specify any optional rules therefore default to all of them -- NONE,LOW_REROLL. Roll 10 times for each combination. Display header. Display time to generate.
```
java -jar tntgen-1.0-SNAPSHOT.jar -r 10 -h -t -e FIFTH -k HUMAN,DWARF -s STR,ADDS
EDITION,OPTIONALRULES,KIN,STAT,COUNT
FIFTH,LOW_REROLL,DWARF,ADDS,6,2
FIFTH,LOW_REROLL,DWARF,ADDS,8,2
FIFTH,LOW_REROLL,DWARF,ADDS,12,1
FIFTH,LOW_REROLL,DWARF,ADDS,13,3
FIFTH,LOW_REROLL,DWARF,ADDS,24,2
FIFTH,LOW_REROLL,DWARF,STR,18,4
FIFTH,LOW_REROLL,DWARF,STR,20,1
FIFTH,LOW_REROLL,DWARF,STR,22,1
FIFTH,LOW_REROLL,DWARF,STR,24,2
FIFTH,LOW_REROLL,DWARF,STR,36,2
FIFTH,LOW_REROLL,HUMAN,ADDS,0,3
FIFTH,LOW_REROLL,HUMAN,ADDS,1,1
FIFTH,LOW_REROLL,HUMAN,ADDS,2,1
FIFTH,LOW_REROLL,HUMAN,ADDS,3,2
FIFTH,LOW_REROLL,HUMAN,ADDS,6,1
FIFTH,LOW_REROLL,HUMAN,ADDS,7,1
FIFTH,LOW_REROLL,HUMAN,ADDS,8,1
FIFTH,LOW_REROLL,HUMAN,STR,9,1
FIFTH,LOW_REROLL,HUMAN,STR,10,2
FIFTH,LOW_REROLL,HUMAN,STR,11,2
FIFTH,LOW_REROLL,HUMAN,STR,12,2
FIFTH,LOW_REROLL,HUMAN,STR,13,1
FIFTH,LOW_REROLL,HUMAN,STR,17,1
FIFTH,LOW_REROLL,HUMAN,STR,18,1
FIFTH,NONE,DWARF,ADDS,2,1
FIFTH,NONE,DWARF,ADDS,5,2
FIFTH,NONE,DWARF,ADDS,6,2
FIFTH,NONE,DWARF,ADDS,7,1
FIFTH,NONE,DWARF,ADDS,9,1
FIFTH,NONE,DWARF,ADDS,10,1
FIFTH,NONE,DWARF,ADDS,16,1
FIFTH,NONE,DWARF,ADDS,19,1
FIFTH,NONE,DWARF,STR,14,2
FIFTH,NONE,DWARF,STR,16,1
FIFTH,NONE,DWARF,STR,18,1
FIFTH,NONE,DWARF,STR,20,3
FIFTH,NONE,DWARF,STR,22,1
FIFTH,NONE,DWARF,STR,28,1
FIFTH,NONE,DWARF,STR,30,1
FIFTH,NONE,HUMAN,ADDS,-6,1
FIFTH,NONE,HUMAN,ADDS,-4,1
FIFTH,NONE,HUMAN,ADDS,-2,2
FIFTH,NONE,HUMAN,ADDS,-1,1
FIFTH,NONE,HUMAN,ADDS,0,1
FIFTH,NONE,HUMAN,ADDS,2,1
FIFTH,NONE,HUMAN,ADDS,3,2
FIFTH,NONE,HUMAN,ADDS,5,1
FIFTH,NONE,HUMAN,STR,5,1
FIFTH,NONE,HUMAN,STR,9,2
FIFTH,NONE,HUMAN,STR,11,1
FIFTH,NONE,HUMAN,STR,12,2
FIFTH,NONE,HUMAN,STR,13,1
FIFTH,NONE,HUMAN,STR,14,2
FIFTH,NONE,HUMAN,STR,16,1
execution time: 350 ms
```
