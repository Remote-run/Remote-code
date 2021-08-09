# Remote-code

Description of the system can be found in the [system_info](doc/system_info.md) document.



## Requirements

#### Docker

The containers are run using docker install instructions are found [here](https://docs.docker.com/get-docker/)

Remember the [post install steps](https://docs.docker.com/engine/install/linux-postinstall/)

Compose is used to start the main services. Install instructions can be found [here](https://docs.docker.com/compose/install/)


#### Maven

To build the project maven is required along with a jdk > 15 

copy friendly install:  `` sudo apt install -y mvn openjdk-16-jre ``

#### Nvidia container toolkit 

The slaves needs the [Nvidia container toolkit](https://github.com/NVIDIA/nvidia-dockerhttps://developer.nvidia.com/cuda-downloads) installed. The installation can be finicky so test the install before proceeding. To test the install run ``` sudo docker run --rm --gpus all nvidia/cuda:11.0-base nvidia-smi``` if all GPU's are you should be OK.



## Run

For the system to run you firstly have to configure a wild-card dns record to point to the master instances ip. The current domain is written in a couple of different config files  but this can be changed with a quick find and replace. 

The traefik instance needs to be provided with a api key to whatever dns provider holds the record. Change the traefik config file to the used provider and inject the correct env vars for that provider. A list of the providers and keys can be found [here](https://doc.traefik.io/traefik/https/acme/#providers). 

The master and slave can bud does not have to run on the same server

- To start the master, run the start_master.sh script

- To start the slave, run the start_slave.sh script







