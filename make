#!/bin/bash

clean() {
	rm *.class logica/*.class
}

build() {
	javac *.java logica/*.java
}

run(){
	build
	java Lluvia
	clean
} 

if declare -f "$1" > /dev/null
then
  "$@"
fi
