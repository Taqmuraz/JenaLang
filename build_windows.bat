rd /s /q build
dir /s /b *.java > sourcelist.txt
javac -target 16 -source 16 -classpath lib/* -d build @sourcelist.txt
del sourcelist.txt

cd build
(echo Main-Class: jena.lang.Program & echo. ) > manifest.txt
jar -cvfm jena.jar manifest.txt jena