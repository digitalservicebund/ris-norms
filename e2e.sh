#!/bin/bash
docker compose stop
docker container prune -f
docker volume rm ris-norms_postgres15-data
docker compose build

projects=( "chromium" "firefox" "msedge" )

for project in "${projects[@]}"
do
    docker compose up -d

    while [ `docker logs ris-norms-webapp-1 | grep -e "Started Application in" -c` -lt 1 ] ;
    do
      echo "sleeping for 1 second and waiting for Spring to boot"
      sleep 1
    done

    cd frontend
    docker build -t ris-norms-playwright-$project --target $project -f DockerfilePlaywright .
    docker run --name ris-norms-playwright-$project -it --rm \
      -e E2E_BASE_URL="http://nginx:8080" \
      -e TZ="Europe/Berlin" \
      --network ris-norms_default \
      -v $(pwd)/test-results:/usr/src/app/test-results \
      ris-norms-playwright-$project
    cd ..
    docker compose stop
    docker container prune -f
    docker volume rm ris-norms_postgres15-data
done
