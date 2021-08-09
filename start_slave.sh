
DOCKER_BUILDKIT=1
COMPOSE_DOCKER_CLI_BUILD=1

# exit when any command fails
set -e
trap popd EXIT

# start the slave
pushd .modules/master/deploy
docker-compose up -d -- build
popd
