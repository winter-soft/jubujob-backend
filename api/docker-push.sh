#!/bin/bash

version=$1

docker build --platform amd64 -t jubujob .
docker tag jubujob ghdcksgml1/jubujob:$1
docker push ghdcksgml1/jubujob:$1
