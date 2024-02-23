#!   /bin/bash -e

npm --prefix ./vscode-extension/ install
npm --prefix ./vscode-extension/ run vscode:pack

./time-machine/gradlew -p ./time-machine spotlessApply
./time-machine/gradlew -p ./time-machine  build
./time-machine/gradlew -p ./time-machine  install
./time-machine/gradlew -p ./time-machine  checkLicense

docker build -t ris-norms-vs-code:001 -f DockerfileVsCode .

docker build -t ris-norms-app:001 --build-arg VITE_VSCODE_URL="$VITE_VSCODE_URL" -f DockerfileApp .
