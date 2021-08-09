
export DOCKER_BUILDKIT=1
export COMPOSE_DOCKER_CLI_BUILD=1

# exit when any command fails
set -e
trap popd EXIT

# start the slave
pushd ./modules/master/deploy
mkdir ./service_db/db_files 2> /dev/null
docker-compose up -d --build
