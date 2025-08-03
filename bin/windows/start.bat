@echo off
echo Start Containers...
cd ../..
docker-compose up --force-recreate
pause