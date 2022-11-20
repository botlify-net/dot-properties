##
## DOT PROPERTIES, 2022
## Dot Properties Makefile
## File description:
## Generic Makefile for Dot Properties
##

#=================================
#	Commands
#=================================

.PHONY:				all \
					install \
					test \
					finstall \
					clean

all:				install

install:
					mvn install

finstall:
					mvn install -DskipTests

test:
					mvn test

clean:
					mvn clean
					rm -rf target | true
