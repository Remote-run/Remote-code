
export DOCKER_BUILDKIT=1
export COMPOSE_DOCKER_CLI_BUILD=1

# exit when any command fails
set -e
trap popd EXIT

# Build the base container
# TODO: should be automated later
docker image build modules/slave/container_envs/_base -t remote-code-base

# start the slave
pushd ./modules/slave
docker-compose up -d --build
