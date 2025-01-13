# Tunnels & Trolls Character Statistics Generator
This is a tool used for Tunnels & Trolls, the second roleplaying game ever published, designed and written by Ken St. Andre, et. al, and published by Flying Buffalo, Inc. This tool, TNTGEN, is designed to roll up a large number of character attributes of all different kindreds, using various rules sets. Output can be used for statistical analysis and comparisons can be made between rules sets, kindreds, etc.   
  
  
Written in Java 1.8, but not sure which other versions it's compatible with, as I haven't bothered to test that. I suspect the newest feature used is ThreadLocalRandom, which was introduced in JDK 7.

## WHAT TNTGEN IS
TNTGEN was born out of the author's curiosity about how different Tunnels & Trolls character kindreds stack up against each other statistically. Also, account for different rules editions and optional rules. It can help to answer such questions that mankind had pondered for decades, such as:

* "If I roll up a bunch of characters, for all kindreds available, according to the 5th Edition T&T rules, how will their combat adds, in aggregate, compare to each other?" 

* "Which percentage of dwarves in the Deluxe Edition of T&T will be created with a Strength of 30 or greater?"

* "What are the average combat adds for an Elf? And show me all (supported) editions."

* "If I rolled up a billion fairies, in the Deluxe Edition of T&T, would even a single one be created with a Constitution score of at least 40? (Enough TARO - Triples Add and Reroll - rolls and it could theoretically happen, but fairies divide their CON by 4.)" [I just tried this and the answer is...No! The highest CON rolled by a fairy was 36, which was rolled only a single time in a billion. -Mark W]

It is a command line program that simply takes some options as input, to indicate which kindreds are of concern, which rules editions are used, how many character rolls should be made, etc. It outputs the totals for each stat value, for the given combinations of kindred, rules editions and so on, in a delimited string format (CSV by default). 

Optionally, enable the "character output" mode, to output a single character's stats per line, instead of aggregated totals. 

(See later in this doc for more info on all the options.)

## WHAT TNTGEN IS NOT
TNTGEN is not a robust application with a graphical user interface. It only (at this time) generated and outputs the core attributes as well as combat adds. Although it can output one character's stats per line, it does not fully generate a single character, i.e. it does not deal with gold, equipment, spells, name your character, etc.

TNTGEN does not *directly* answer all of the above questions (see [WHAT TNTGEN IS](https://github.com/mwrynn/tntgen/?tab=readme-ov-file#what-tntgen-is) above). It generates many characters (you decide how many) and outputs *totals* aka *sums* for each *value* of the stats you're concerned with. So if you wanted to answer the aforementioned question about the average combat adds for an Elf, this program would give you the total number of times an Elf was rolled with 10 adds, with 9 adds, etc. for the given parameters. But it would not give you the average. You would have to find a way to calculate the average yourself (calculator, spreadsheet software, SQL database, etc.)

## HOW TO RUN THIS PROGRAM:

There are two ways to run this program: download the binary and run it, or build it yourself and run it.

## DOWNLOAD AND RUN TNTGEN:
### PREREQUISITES:
TNTGEN is a Java program, so you must have a Java Runtime Environment, version 8.0 or greater, installed on your machine. 
To test whether you have a compatible Java version installed, open your Command Prompt or Terminal and run:
```
java -version
```
The output should be something like:
```
openjdk version "17.0.10" 2024-01-16 LTS
OpenJDK Runtime Environment Corretto-17.0.10.7.1 (build 17.0.10+7-LTS)
OpenJDK 64-Bit Server VM Corretto-17.0.10.7.1 (build 17.0.10+7-LTS, mixed mode, sharing)
```
It may vary from the above, but the key things are 1) it didn't tell me command not found, so we have confirmed that Java is installed, and 2) we can see the version is 17.0.10 is installed, which is >= 8. So we should be good to go.

If you do NOT have Java installed, try downloading it from [here](https://www.java.com/en/download/manual.jsp).

### DOWNLOAD TNTGEN
You can download the latest version of TNTGEN (download .jar file) from [here](https://github.com/mwrynn/tntgen/releases/latest) on Github.

### RUN TNTGEN
1. Move the tntgen jar file from your Downloads folder to wherever you'd like to keep it.
2. Open your Command Prompt or Terminal.
3. Navigate to the folder you plaved the jar file. (e.g. `cd \stuff`)
4. Give it a test run by running the command such as (replace the version number with the one for the file you downloaded): `java -jar tntgen-0.1.jar -r 10 -h -t -e FIFTH -k HUMAN,DWARF -s STR,ADDS`
5. If you see output, then hooray, it worked! Now you can scroll to the bottom of this README to see the full set of options available.
6. Have fun!

## BUILDING TNTGEN YOURSELF:
For now to run this program you must build it from the source code yourself. This may be new to you if you're not a programmer, but for now I have not attached a build to this github repo, so that's what we've got. :) 

### PREREQUISITES:
You need to have the JDK 8 or greater installed, and have Git and Maven installed.

#### JDK:
You can download a JDK from [here](https://www.oracle.com/java/technologies/downloads/). Then install it.

To verify that you have a JDK installed and available in your path, you can try running the following from your command prompt/terminal:
```
javac -version
```

If all is well, the output should look something like:
```
$ javac -version
javac 17.0.10
```
#### GIT
You can download Git from [here](https://git-scm.com/downloads). Then install it. 

To verify that it is installed and in your path, you can try running the following from your command prompt/terminal:

```
git --version
```

If all is well, the output should look something like:
```
$ git --version
git version 2.39.5 (Apple Git-154)
```

#### MAVEN
You can download Maven from [here](https://maven.apache.org/download.cgi). Then install it.

To verify that it is installed and in your path, you can try running the following from your command prompt/terminal:
```
mvn --version
```
If all is well, the output should look something like:
```
$ mvn --version
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: /opt/homebrew/Cellar/maven/3.9.9/libexec
Java version: 17.0.10, vendor: Amazon.com Inc., runtime: /Users/mwrynn/.sdkman/candidates/java/17.0.10-amzn
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "15.1.1", arch: "aarch64", family: "mac"
```

(The key thing to look for when verifying these programs are installed is that you get no error such as `command not found`.)

### Downloading, Building and Running TNTGEN:
1. Download this code repository by `git clone`ing it. To do so, enter in your command prompt/terminal:
```
git clone https://github.com/mwrynn/tntgen.git
```

This will create a directory called `tntgen`, populated with all the files in this code repository.

2. Go the the newly created `tntgen` directory in your command prompt/terminal. For example: `cd ./tntgen`

3. Build `tntgen` by running the maven command: `mvn package`. This should output a whole bunch of stuff, but what you're looking for is the very end of the output to look something like:
```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.936 s
[INFO] Finished at: 2025-01-01T14:41:26-05:00
[INFO] ------------------------------------------------------------------------
```

4. This should create a jar file, the path being something like `./tntgen/target/tntgen-1.0-SNAPSHOT.jar`. In other words, the Maven command will created a directory called `target` under the `tntgen` directory. It will then populate `target` with a a file with the `.jar` extension called `tntgen-1.0-SNAPSHOT.jar` (or similar). This is the jar to execute with your java command. (See below.)

5. Run the `java` command. The exact command depends on the options you wish to set. Below is an example command that you can try for starters, then modify it as you see fit:
```
java -jar tntgen-1.0-SNAPSHOT.jar -r 10 -h -t -e FIFTH -k HUMAN,DWARF -s STR,ADDS
```
The above command means: Generate 10 rolls for each combination of kin HUMAN and DWARF, stats STR and ADDS, FIFTH edition only, do not specify any optional rules therefore default to all of them -- NONE,LOW_REROLL. Roll 10 times for each combination. Display header. Display time to generate.

More detailed info about the command line arguments available:

```
usage: tntgen
 -?,--help              display this usage text
 -c,--char-output       whether to output individual character stats or
                        aggregated stats; default is false (aggregated)
 -d,--delimiter <arg>   output field delimiter; default is ,
 -e,--edition <arg>     comma-separated list of the rules editions to use. Valid editions at this time: FIFTH,DELUXE
 -h,--header            output column headers
 -k,--kin <arg>         comma-separated list of the kindred to collect
                        stats for. See kin_conf.yaml for valid kin.
 -n,--kin-conf <arg>    path to non-default kin_conf.yaml, which specified
                        kin available, associated them with rules editions
                        and defines attribute multipliers
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

Full example of the command from above, execution and output. 
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

Optional kin configuration:
You can specify a kindred configuration instead of using the default, which can be found [here](https://github.com/mwrynn/tntgen/blob/master/src/main/resources/kin_conf.yaml). The kindred configuration defines which kindred exist for which editions, and which attributes are multiplied by which factor 

This feature exists but complete documentation is a TODO

Enjoy! If you find any bugs or have any cool ideas for features/improvements, write to mwrynn-at-gmail-dot-com.

