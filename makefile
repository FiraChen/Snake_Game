default:
	javac -sourcepath src src/*.java -d out/src

run: default
	java -cp out/src Main

clean:
	-@rm out/src/production/*.class
	-@rm out/src/*.class
