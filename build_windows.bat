rd /s /q build
dir /s /b *.java > sourcelist.txt
javac -target 11 -source 11 -classpath lib/* -d build @sourcelist.txt
del sourcelist.txt

cd build
(echo Main-Class: jena.lang.Program & echo. ) > manifest.txt
jar -cvfm jena.jar manifest.txt jena
cd ..
start /B run_windows.bat