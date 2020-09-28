#!/bin/bash

ensure_dependency() {
  if ! which "$1" &>/dev/null ; then
    echo "$1 not found"
    exit 1
  fi
}

ensure_dependency docker-compose

DIR=$PWD

docker-compose -f $DIR/docker/cluster-compose.yml up
# docker-compose -f $DIR/docker/cluster-compose.yml down