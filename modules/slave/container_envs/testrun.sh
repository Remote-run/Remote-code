pushd  ./_base && \
    ./build.sh &&\
    popd && pushd ./python_tensorflow && \
    ./build_and_run.sh  
