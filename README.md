# The AutoMower program #

Source code : [https://github.com/frecco75/mowitnow](https://github.com/frecco75/mowitnow)
## Technical view ##

| **Build** | **Coverage** | **Code Quality** | **Dependencies** | **Documentation** |
|-----------|--------------|------------------|------------------|-------------------|
|[![Build Status](https://travis-ci.org/frecco75/mowitnow.svg?branch=master)](https://travis-ci.org/frecco75/mowitnow) |[![Coverage Status](https://coveralls.io/repos/github/frecco75/mowitnow/badge.svg?branch=master)](https://coveralls.io/github/frecco75/mowitnow?branch=master) |[![Codacy Badge](https://api.codacy.com/project/badge/Grade/61464999eb554982bea6483a4a401915)](https://www.codacy.com/app/fabien-recco/mowitnow?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=frecco75/mowitnow&amp;utm_campaign=Badge_Grade) |[![Known Vulnerabilities](https://snyk.io/test/github/frecco75/mowitnow/badge.svg)](https://snyk.io/test/github/frecco75/mowitnow) |[![Documentation Status](https://readthedocs.org/projects/mowitnow/badge/?version=latest)](http://mowitnow.readthedocs.org/en/latest/README/)|

### Required configuration ###
*  JDK 13
*  Maven 3
*  Git

### Technologies ####
*  Functional programming: [Java 13](https://www.oracle.com/java/technologies/javase-jdk13-downloads.html), [Vavr](http://www.vavr.io/)
*  Tests: [JUnit](http://junit.org/), [AssertJ](http://joel-costigliola.github.io/assertj/index.html), [Mockito](http://mockito.org/)
*  Behavior Driven Development: [JBehave](http://jbehave.org/)
*  Code generation: [Lombok](https://projectlombok.org)
*  UML: [PlantUML](http://plantuml.com)

## Running  ##

### Running from your IDE ###

*  The **Main** class
*  The automated unit testing class **MainTest**
*  The JUnitStory **AutoMowerStories** (*acceptance testing*)

### Compilation ###
```shell script
./mvnw clean install
```
This command line will create the archive file **automower-1.0.0-SNAPSHOT.jar** in the */target* directory.

### Running ###
```shell script
java -jar target/automower-1.0.0-SNAPSHOT.jar [file]
```

### The algorithm description ###
1.  Parsing of the input file
2.  Creation of instructions list and needed objects in memory
3.  Execution of instructions

![Sequence diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/frecco75/mowitnow/master/src/main/resources/architecture/activity.puml)


### Implementation details ###

The implementation uses functional programming and immutable data structures. No if/else or loop blocks.

## Specifications ##

### Goal ###

Build a java program that implement the following mower’s specification.

### The tasks ###

The company X wants to develop an auto­mower for square surfaces.

The mower can be programmed to go throughout the whole surface. Mower's position is represented by coordinates (X,Y) and a characters indicate the orientation according to cardinal notations (N,E,W,S).
The lawn is divided in grid to simplify navigation.

For example, the position can be 0,0,N, meaning the mower is in the lower left of the lawn, and oriented to the north.

To control the mower, we send a simple sequence of characters. Possibles characters are L,R,F. L and R turn the mower at 90° on the left or right without moving the mower. F means the mower move forward from one space in the direction in which it faces and without changing the orientation.

If the position after moving is outside the lawn, mower keep it's position. Retains its orientation and go to the next command.

We assume the position directly to the north of (X,Y) is (X,Y+1).

To program the mower, we can provide an input file constructed as follows:

The first line correspond to the coordinate of the upper right corner of the lawn. The bottom left corner is assumed as (0,0).
The rest of the file can control multiple mowers deployed on the lawn. Each mower has 2 next
lines :

The first line give mower's starting position and orientation as "X Y O". X and Y being the
position and O the orientation.

The second line give instructions to the mower to go throughout the lawn. Instructions are
characters without spaces.

Each mower move sequentially, meaning that the second mower moves only when the first has
fully performed its series of instructions.

When a mower has finished, it give the final position and orientation.

### Example​ ###

input file
```
5 5
1 2 N
LFLFLFLFF
3 3 E
FFRFFRFRRF
```

result
```
1 3 N
5 1 E
```
