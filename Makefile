.PHONY: compile run fmt lint clean

compile:
	cd $(module) && mvn -DskipTests compile

run: compile
	mkdir -p $(module)/run
	cd $(module)/run && mvn -f ../pom.xml exec:java

fmt:
	mvn spotless:apply

lint:
	mvn checkstyle:check

clean:
	mvn clean
