#!/bin/sh
version=$1
if [ -z "${version}" ]; then 
   echo Usage: ${0} version
   exit -1 ;
fi
chmod 777 *.sh
docker image build -t qijunbo/oss:${version}  .
docker images 
