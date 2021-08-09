
export DOCKER_BUILDKIT=1
export COMPOSE_DOCKER_CLI_BUILD=1

# exit when any command fails
set -e
trap popd EXIT

# start the slave
pushd ./modules/master/deploy
mkdir -p ./service_db/db_files
docker-compose up -d --build
