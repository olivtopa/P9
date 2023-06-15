# Mongo

docker pull mongo

docker run --env=MONGO_INITDB_ROOT_USERNAME=admin --env=MONGO_INITDB_ROOT_PASSWORD=password --env=HOME=/data/db --volume=/data/configdb --volume=/data/db -p 27017:27017 -d mongo:latest

# Docker

docker build Application/dr-notes -t dr-notes:1.0.0