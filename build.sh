#!/bin/bash

DIR=$PWD

cd $DIR/uaa && ./gradlew clean -x test build -Pprod jibDockerBuild ;
cd $DIR/demo1 && ./gradlew clean -x test build -Pprod jibDockerBuild ;
cd $DIR/demo2 && ./gradlew clean -x test build -Pprod jibDockerBuild ;
