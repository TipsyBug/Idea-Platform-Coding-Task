#!/bin/bash
javac -cp "libs/jackson-annotations-2.17.1.jar:libs/jackson-core-2.17.1.jar:libs/jackson-databind-2.17.1.jar" src/Main.java -d .
java -cp ".:libs/jackson-annotations-2.17.1.jar:libs/jackson-core-2.17.1.jar:libs/jackson-databind-2.17.1.jar" Main
