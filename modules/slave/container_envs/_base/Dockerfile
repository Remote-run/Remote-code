FROM  nvidia/cuda:11.3.1-cudnn8-runtime-ubuntu20.04

RUN apt update && apt upgrade -y && apt install -y curl git
RUN curl -fsSL https://code-server.dev/install.sh | sh

# For og fikse base image til nvidia som ikke virke yay
#RUN apt update && apt upgrade -y && apt install -y software-properties-common
#RUN add-apt-repository -y ppa:graphics-drivers/ppa


RUN mkdir /root/remote-code-project

WORKDIR /root/remote-code-project

RUN code-server \
    --install-extension ms-python.python \
    --install-extension redhat.java \
    --install-extension vscjava.vscode-maven \
    --install-extension donjayamanne.githistory \
    --install-extension eamodio.gitlens \
    --install-extension coenraads.bracket-pair-colorizer \
    --install-extension esbenp.prettier-vscode \
    --install-extension pkief.material-icon-theme



COPY vs_code_config.json /root/.local/share/code-server/User/settings.json

ENV GIT_BASE_REPO=""

EXPOSE 80

RUN mkdir /root/.ssh
RUN ssh-keyscan github.com >> ~/.ssh/known_hosts

CMD  git clone $GIT_BASE_REPO  &>2 /dev/null; \
  code-server \
    --bind-addr 0.0.0.0:80 \
    --auth  none \
    /root/remote-code-project/*

#    --user-data-dir  /root/remote-code-project/* \

#root@b724c1878200:/# code-server --help
#[2021-06-23T11:47:28.508Z] info  Wrote default config file to ~/.config/code-server/config.yaml
#code-server 3.10.2 387b12ef4ca404ffd39d84834e1f0776e9e3c005
#
#Usage: code-server [options] [path]
#
#Options
#      --auth                 The type of authentication to use. [password, none]
#      --password             The password for password authentication (can only be passed in via $PASSWORD or the config file).
#      --hashed-password      The password hashed with SHA-256 for password authentication (can only be passed in via $HASHED_PASSWORD or the config file).
#                             Takes precedence over 'password'.
#      --cert                 Path to certificate. A self signed certificate is generated if none is provided.
#      --cert-host            Hostname to use when generating a self signed certificate.
#      --cert-key             Path to certificate key when using non-generated cert.
#      --disable-telemetry    Disable telemetry.
#      --disable-update-check Disable update check. Without this flag, code-server checks every 6 hours against the latest github release and
#                             then notifies you once every week that a new release is available.
#   -h --help                 Show this output.
#      --open                 Open in browser on startup. Does not work remotely.
#      --bind-addr            Address to bind to in host:port. You can also use $PORT to override the port.
#      --config               Path to yaml config file. Every flag maps directly to a key in the config file.
#      --socket               Path to a socket (bind-addr will be ignored).
#   -v --version              Display version information.
#      --user-data-dir        Path to the user data directory.
#      --extensions-dir       Path to the extensions directory.
#      --list-extensions      List installed VS Code extensions.
#      --force                Avoid prompts when installing VS Code extensions.
#      --install-extension    Install or update a VS Code extension by id or vsix. The identifier of an extension is `${publisher}.${name}`.
#                             To install a specific version provide `@${version}`. For example: 'vscode.csharp@1.2.3'.
#      --enable-proposed-api  Enable proposed API features for extensions. Can receive one or more extension IDs to enable individually.
#      --uninstall-extension  Uninstall a VS Code extension by id.
#      --show-versions        Show VS Code extension versions.
#      --proxy-domain         Domain used for proxying ports.
#   -e --ignore-last-opened   Ignore the last opened directory or workspace in favor of an empty window.
#   -n --new-window           Force to open a new window.
#   -r --reuse-window         Force to open a file or folder in an already opened window.
# -vvv --verbose              Enable verbose logging.
#      --link                 (beta) Securely bind code-server via our cloud service with the passed name. You'll get a URL like
#                             https://hostname-username.cdr.co at which you can easily access your code-server instance.
#                             Authorization is done via GitHub.
