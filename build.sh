#!/bin/bash

DIR=$PWD

cd $DIR/uaa && ./gradlew clean build -x test -x integrationTest -Pprod jibDockerBuild ;
cd $DIR/demo1 && ./gradlew clean build -x test -x integrationTest -Pprod jibDockerBuild ;
cd $DIR/demo2 && ./gradlew clean build -x test -x integrationTest -Pprod jibDockerBuild ;
