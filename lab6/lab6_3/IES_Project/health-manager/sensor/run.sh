#!bin/bash

docker build -t sensor .
docker run -i -t --network=host --name sensor sensor
