docker image build . -t test_remote_code

docker run -it --rm \
    --name test \
    --gpus all \
    --network remote_code_net  \
    -e GIT_BASE_REPO="https://github.com/Remote-run/remote-code-tf-test.git" \
    test_remote_code


#    -v "$PWD/container_dot_conf:/home/coder/.config" \

# -p 127.0.0.1:8080:8080

#    --label "traefik.http.routers.myrouter.rule=Host(\`{{ trimPrefix \`/\` .Name }}.remote-code.uials.no\`)" \

#
  #  --label "traefik.http.routers.myrouter.service=add-tls-service@file" \
# "traefik.http.routers.myrouter.service=myservice"