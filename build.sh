#!/bin/sh
echo timestamp=`date "+%Y-%m-%d %H:%M:%S"` >src/main/resources/version.properties
echo version=${version} >>src/main/resources/version.properties

chmod 770 gradlew
./gradlew clean build
workfolder=/opt/apps
mkdir -p ${workfolder}
app=oss
version=1.0

mkdir -p ${workfolder}/${app}
sudo systemctl stop ${app}
cp -vpf build/libs/${app}.jar  ${workfolder}/${app}/${app}.jar
chmod 755 ${workfolder}/${app}/${app}.jar
sudo ln -sf  ${workfolder}/${app}/${app}.jar  /etc/init.d/${app}
sudo systemctl daemon-reload
sudo systemctl enable ${app}
sudo systemctl start ${app}

