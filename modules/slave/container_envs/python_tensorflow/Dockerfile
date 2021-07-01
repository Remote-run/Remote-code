FROM remote-code-base

RUN apt update && apt upgrade -y && apt install -y curl python3 python3-pip

# For og fikse base image til nvidia som ikke virke yay
#RUN apt update && apt upgrade -y && apt install -y software-properties-common
#RUN add-apt-repository -y ppa:graphics-drivers/ppa

RUN pip3 install tensorflow pylint
