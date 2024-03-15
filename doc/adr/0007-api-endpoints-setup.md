# 7. API endpoints setup

Date: 2024-03-07

## Status

Accepted

## Context

We need to support a growing list of endpoints. At the same time, we want to avoid repeated discussions about what each endpoint should look like, and reduce the time spent thinking about which information should be available where.

This ADR outlines our idea of sensible defaults for how endpoints should be structured, and how they should behave. The goal is to have a shared understanding that makes API endpoint-related decisions easier, and the structure and behavior of those endpoints more predictable.

## Decision

Our proposal consists of a simple foundation that supports our current needs. It is also easy to extend to support requirements we will very likely have in the near future.

We're aiming for a REST-ful interface, meaning that we ask the API for resources, not functionality. However we are not dogmatic about it.

### Foundation

We provide create, read, update, and delete endpoints for individual resources (not all resources need to support all operations), as well as list endpoints for listing all available resources of a certain type.

An individual resource endpoint returns all relevant top level properties of the resource. It does not return nested objects or other resources that are referenced. In order to fetch those, you will need to know which related resources are available, as well as the endpoint for requesting them, and then request them separately.

List endpoints return the same data structure as the corresponding individual resource endpoints, just as an array containing multiple items of that type.

Endpoints that change data (e.g. `POST`, `PUT`) return the updated data in their response. This can be used by the requester (e.g. the frontend) to conveniently synchronize its state with the current backend state.

Example:

```ts
type Law = {
  eli: string;
  title: string;
  articles: Article[];
};

type Article = {
  eid: string;
  title: string;
  xml: string;
};
```

...would correspond to the following endpoints:

| Operation                      | Notes                                                        |
| ------------------------------ | ------------------------------------------------------------ |
| `GET /laws`                    | Returns an array of `{ eli: string; title: string }`         |
| `GET /laws/:eli`               | Returns a single law of type `{ eli: string, title: string}` |
| `GET /laws/:eli/articles`      | Returns the articles of the law                              |
| `GET /laws/:eli/articles/:eid` | Returns a single article                                     |

### Extension 1: Support for nested resources

In the above example, having to know that laws contain articles as well as knowing how to fetch them is inconvenient, and often also inefficient. We're planning to solve this eventually by including references to nested resources inside the parent resource following the [Hypertext Application Language](https://en.wikipedia.org/wiki/Hypertext_Application_Language) convention.

### Extension 2: Supporting different data formats

Many of our endpoints need to provide output and accept input in both JSON and XML. We're planning solving this by sending the [HTTP `Accept`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept) header with API requests. Based on that, the backend can then respond with the appropriate data format.

### Extension 3: Unified endpoints via ELI/eId

Many of the resources that currently exist as individual endpoints are uniquely identified by a combination of ELI + eId. This means that the following example:

```
GET /laws/:eli/articles/:eid
```

... could just as well be:

```
GET /:eli/:eid
```

Once we need the ability to fetch many different elements inside laws (or anything for that matter), providing an endpoint that can resolve the element by its ELI + eId will be easier and more flexible. It will also be more "correct", because an eId can point to many things other than articles. So for the `articles/:eid` endpoint to make sense, we would need to artifically constrain the possible values of the eId.

## Consequences

- We have established a simple pattern for out API endpoints that is easy to implement and predictable in its structure and behavior.

- We have proposals to over time make the API more convenient and efficient to use for requesters, at the cost of increased complexity in the backend.
