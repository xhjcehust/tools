#!/bin/sh

if ! [ -d "scripts" ]; then
    echo "execute in dir out of scripts"
    exit 1
fi

if [ -z "$1" ]; then
    echo "install.sh <targetFile>"
    exit 1
fi

dir=$(pwd)
mvn package -DtargetFile="$1"
cd "scripts" || exit

lowerArg=$(echo "$1"|tr '[:upper:]' '[:lower:]')
installDir="/usr/local/bin/tools";
targetDir="../target/scripts";
targetFile="$targetDir/$lowerArg"
jarFile="$installDir/$lowerArg".jar

if [ ! -d  $targetDir ]
then
	mkdir -p $targetDir
fi

echo "#!/bin/sh" > "$targetFile"
echo "java -jar $jarFile" >> "$targetFile"
chmod +x "$targetFile"
cp "$targetFile" "$installDir/"
cp "$dir/target/tools-1.0-SNAPSHOT.jar" "$jarFile"
