#!/usr/bin/env bash

sbt backend/assembly
sbt telegram/assembly

cd backend
docker build -t shopping-list/backend .
cd ../telegram/
docker build -t shopping-list/telegram .

cd ..
docker save -o backend.tar shopping-list/backend:latest
docker save -o telegram.tar shopping-list/telegram:latest

ssh -t root@sl 'docker-compose down'

scp backend.tar root@sl:~/
scp telegram.tar root@sl:~/
scp docker-compose.yml root@sl:~/

ssh -t root@sl 'docker load -i backend.tar'
ssh -t root@sl 'docker load -i telegram.tar'
ssh -t root@sl 'docker-compose up -d'