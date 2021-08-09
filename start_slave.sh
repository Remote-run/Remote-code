
# exit when any command fails
set -e
trap popd EXIT

# build the project
pushd ./modules
mvn clean install
popd

# start the slave
pushd .modules/master/deploy
docker-compose up -d -- build
popd
