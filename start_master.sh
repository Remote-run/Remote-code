

export DOCKER_BUILDKIT=1
export COMPOSE_DOCKER_CLI_BUILD=1

# exit when any command fails
set -e
trap popd EXIT

# build the project
pushd ./modules/auth/deploy
docker-compose up --build -d
popd

# build the project
pushd ./modules/frontend/deploy
docker-compose up --build -d
popd

# start the support systems, kafka and traefik
pushd ./modules/
docker-compose -f master.docker-compose.yaml up -d -- build
popd

# start the master
pushd .modules/master/deploy
docker-compose up -d -- build
popd
