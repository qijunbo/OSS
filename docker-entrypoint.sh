#!/bin/sh

#set -e

# Allows to use sed but with user input which can contain special sed characters such as \, / or &.
# $1 - the text to search for
# $2 - the replacement text
# $3 - the file in which to do the search/replace

function safesed {
  sed -i "s/$(echo $1 | sed -e 's/\([[\/.*]\|\]\)/\\&/g')/$(echo $2 | sed -e 's/[\/&]/\\&/g')/g" $3
}

safesed CONTEXTPATH ${CONTEXTPATH:-} /config/application.yml
safesed APP_DB_HOST ${APP_DB_HOST:-db} /config/application.yml
safesed APP_DB_PORT ${APP_DB_PORT:-3306} /config/application.yml
safesed APP_DB_USER ${APP_DB_USER:-root} /config/application.yml
safesed APP_DB_PASSWORD ${APP_DB_PASSWORD:-sunway123###} /config/application.yml
safesed APP_DATABASE ${APP_DATABASE:-oss} /config/application.yml

jarfile=`ls *jar`
if  [ -f "$jarfile" ]; then
    echo $jarfile
    java  $JAVA_OPTS -jar  ${jarfile}
fi

echo '已经执行完所有指令, 进入空转程序.'
while sleep 60; do

  echo `ps -aux`

done

