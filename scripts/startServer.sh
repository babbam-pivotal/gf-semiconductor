#!/bin/bash

export CLASSPATH=$CLASSPATH:/Users/hshin/Documents/workspace/git/hwshin16/gf-semiconductor/lib/gemfire-greenplum-2.3.0.jar

# Issue commands to gfsh to start locator and launch a server
echo "Starting locator and server..."
gfsh <<!
connect

start locator --name=locator --bind-address=localhost --port=10334 --include-system-classpath --initial-heap=50m --max-heap=50m

start server --name=server1 --server-port=40404 --cache-xml-file=server.xml --include-system-classpath --initial-heap=500m --max-heap=500m
start server --name=server2 --server-port=40406 --cache-xml-file=server.xml --include-system-classpath --initial-heap=500m --max-heap=500m

# change the name/ location of this jar file depending on where you launch this script

deploy --jar=/Users/hshin/Documents/workspace/git/hwshin16/gf-semiconductor/target/semiconductor-0.0.1-SNAPSHOT.jar

list members;
list regions;
!
