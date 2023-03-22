
ficheroJar := tablero.jar

limpiar:
	rm -rf html
	rm -f $(ficheroJar)
	rm -rf bin
compilar:limpiar
	mkdir bin
	find . -name "*.java" | xargs javac -cp bin -d bin

jar:compilar
	jar cvfm $(ficheroJar) manifest.txt -C bin .
doc:compilar
	mkdir html
	find src -name "*.java" | xargs javadoc -d html -encoding utf-8 -docencoding utf-8 -charset utf-8
