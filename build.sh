find . -name "*.java" > sourcelist.txt
rm -r build
mkdir build
javac -classpath "lib/*" -d build @sourcelist.txt
rm sourcelist.txt
cd build
echo Main-Class: jena.lang.Program >> manifest.txt
jar -cvfm jena.jar manifest.txt jena