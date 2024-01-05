# which version?
FROM codercom/code-server:4.19.1-bullseye

USER coder

# Use bash shell
ENV SHELL=/bin/bash

# Fix permissions for home folder
RUN sudo chown -R coder:coder /home/coder

# install java
RUN sudo apt-get update && sudo apt-get install openjdk-17-jre -y

# copy time machine
COPY ./time-machine/build/install/ris-norms-time-machine/bin /home/coder/bin
COPY ./time-machine/build/install/ris-norms-time-machine/lib /home/coder/lib

# add our extension
COPY ./vscode-extension/ris-norms-*.vsix /home/coder/ris-norms.vsix
RUN code-server --install-extension /home/coder/ris-norms.vsix

# copy example files

#  add extensions Red Hat Xml, change language
RUN code-server --install-extension redhat.vscode-xml

RUN PATH="$PATH:/home/coder/bin"
WORKDIR /home/coder/workspace1

# Port
ENV PORT=8080
