# 6. Deploy Frontend and Backend as separate images

Date: 2024-02-08

## Status

Accepted

## Context

Within the NeuRIS project there has been an unresolved issue that lead to the decision of the frontend and backend being deployed
as separate images, as documented here [ADR Nr. 8 Deploy frontend in separate image](https://github.com/digitalservicebund/ris-backend-service/blob/main/doc/adr/0008-deploy-frontend-in-separate-image.md).

We aimed to have just one docker image to deploy that consists of both the backend and the frontend, with the goal of
tackling the issue raised there. The idea would be to serve the frontend files as static assets using Spring Boot.

The solution for solving the problem turned out to be more complicated than having two separate images.

## Decision

We have evaluated how to solve the problem of routing the paths used by the client side router to the `index.html` file.
Spring Boot does not support a simple setup of such routing. We would need to create controllers for all base-paths that
should be handled by the client side routing. A way to only route base-paths that are not `/api` or alternately all
unhandled paths to the `index.html` file does not exist with Spring Boot. This is different to e.g. nginx.
A further problem would be the handling of encoded `/` (`%2F`) in paths, as we are currently using for elis. For Spring
Boot to allow these we would need to disable a security rule.

By packaging both components together, we could have achieved a more streamlined deployment process
and minimized the complexities associated with coordinating separate frontend and backend deployments.

We decided against combining the frontend and backend into one image.

## Consequences

- **Simpler frontend route configuration:** We only need to create new routes in the frontend and not change the backend as well.
- **Separation of frontend and backend:** The backend does not need special code to handle the serving of the frontend.
- **Harder Deployment:** Combining the frontend and backend into a single Docker image would simplify parts of the deployment process.
- **Versioning:** We might need to consider versioning differences between frontend and backend. This could be
- **Less Resource Sharing:** Sharing the same Docker image for both frontend and backend could allow for more efficient resource utilization, as certain dependencies and configurations could be shared between the two components.
- **Monitoring and Logging:** Monitoring and logging are simple to distinguish between frontend and backend activities.
