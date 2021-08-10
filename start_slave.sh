
export DOCKER_BUILDKIT=1
export COMPOSE_DOCKER_CLI_BUILD=1

# exit when any command fails
set -e
trap popd EXIT

# Build the base container
# TODO: should be automated later
modules/slave/container_envs/_base/build.sh

# start the slave
pushd ./modules/master/deploy
docker-compose up -d --build
