#!   /bin/bash -e

./time-machine/gradlew -p ./time-machine spotlessApply
./time-machine/gradlew -p ./time-machine  build
./time-machine/gradlew -p ./time-machine  install
./time-machine/gradlew -p ./time-machine  checkLicense

docker build -t ris-norms-app:001 -f DockerfileApp .
