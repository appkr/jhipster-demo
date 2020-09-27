#!/bin/bash

cd $HOME/jhipster-demo/uaa && ./gradlew clean -x test build -Pprod jibDockerBuild ;
cd $HOME/jhipster-demo/demo1 && ./gradlew clean -x test build -Pprod jibDockerBuild ;
cd $HOME/jhipster-demo/demo2 && ./gradlew clean -x test build -Pprod jibDockerBuild ;