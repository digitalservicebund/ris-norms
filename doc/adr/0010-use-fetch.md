# 10. Usage of `useFetch`

Date: 2024-06-14

## Status

Accepted

## Context

In our frontend development, we frequently need to manage common states for API requests such as loading, errors, empty states, and reloading when parameters change. Writing this code repeatedly is inefficient and likely to lead to inconsistent patterns. We need a reusable solution. We're also looking for something that integrates seamlessly with Vue’s reactive approach.

## Decision

We have decided to use the [`useFetch`](https://vueuse.org/core/useFetch/#usefetch) method from the [VueUse utility collection](https://vueuse.org/) to streamline our API request handling. This method will help manage the repetitive tasks associated with API requests.

`useFetch` can be used in many different ways. We've aligned on the following patterns:

### Services

For each resource, we create a `use<ResourceName>Service` composable. This composable handles:

- Constructing the URL (including inserting parameters and canceling the call if required parameters are missing)
- Passing the URL to `useFetch`
- Any additional setup required for the API call
- Returning the result

We also create shorthand methods for specific use cases to simplify the service usage. For example: `useNormService` has shorthand methods such as `useGetNorm`, `useGetNormHtml`, and `usePutNormXml`.

These shorthands act as presets for certain configurations of the service.

### Composables

When necessary, we create additional composables to optimize or simplify the usage of services for specific cases. For instance, if we need to fetch and update a resource and keeping the fetched data and the updated data in sync, we can accomplish this with a composable.

Using composables is optional; services can also be used directly within components.

### Canceling requests

`useFetch` requires valid URLs at all times. When we cannot construct a meaningful URL due to missing parameters, we introduce a constant called `INVALID_URL`. We add a `beforeFetch` hook to `useFetch` to abort the request if the URL is `INVALID_URL`. This way, if a URL cannot be constructed, returning `INVALID_URL` prevents unnecessary requests.

### Testing

We focus our testing on custom logic added on top of `useFetch`, not on `useFetch` itself. Specifically, tests include:

- Services:
  - Correct URL construction
  - Proper reloading when needed
  - No loading when required parameters are missing
- Service presets:
  - Correct service invocation
  - Proper header additions
- Composables:
  - Correct service or preset invocation

## Consequences

Adopting useFetch reduces repetitive code and ensures code consistency across the application.
