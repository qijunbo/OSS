#!/bin/sh

version=$1
if [ -z "${version}" ]; then
   echo "[error:] version is not specified."
   echo "[usage:] $0 <version>"     
   exit 1
fi

docker login --username=junboqi@foxmail.com registry.cn-shanghai.aliyuncs.com
docker tag qijunbo/oss:${version} registry.cn-shanghai.aliyuncs.com/qijunbo/oss:${version}
docker push registry.cn-shanghai.aliyuncs.com/qijunbo/oss:${version}
