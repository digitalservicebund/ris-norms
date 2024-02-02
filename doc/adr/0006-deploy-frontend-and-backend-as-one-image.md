# 4. Deploy Frontend and Backend as one image

Date: 2024-29-01

## Status

Accepted

## Context

Within the NeuRIS project there has been an unresolved issue that lead to the decision of the frontend and backend being deployed
as separate images, as documented here [ADR Nr. 8 Deploy frontend in separate image](https://github.com/digitalservicebund/ris-backend-service/blob/main/doc/adr/0008-deploy-frontend-in-separate-image.md).
We aim to have just one docker image to deploy that consists of both the backend and the frontend, with the goal of
tackling the issue raised there.


## Decision

After careful consideration and evaluation of our architecture and deployment strategy, we have decided to consolidate
the frontend and backend into a single Docker image. This approach simplifies deployment and enhances the manageability
of our application stack. By packaging both components together, we can achieve a more streamlined deployment process
and minimize the complexities associated with coordinating separate frontend and backend deployments.

## Consequences

- **Simplified Deployment:** Combining the frontend and backend into a single Docker image simplifies the deployment process. This reduces the likelihood of deployment-related issues and enhances overall system stability.
- **Easier Versioning:** With a unified Docker image, versioning becomes more straightforward. Changes to both the frontend and backend can be managed and released together, ensuring compatibility and consistency across different environments.
- **Resource Sharing:** Sharing the same Docker image for both frontend and backend allows for efficient resource utilization, as certain dependencies and configurations can be shared between the two components.
- **Potential Overhead:** While a combined Docker image offers simplicity, it may introduce a level of overhead, especially if there are significant differences in the update frequency or lifecycle of the frontend and backend components.
- **Limited Scalability:** If scalability requirements for the frontend and backend differ significantly, combining them into a single Docker image might limit the scalability options for each component independently. But scalability won't really be the problem in NeuRIS.
- **Monitoring and Logging:** Monitoring and logging may need to be carefully configured to distinguish between frontend and backend activities within the unified Docker container.
