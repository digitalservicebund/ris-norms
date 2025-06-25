# 19. Pagination for Works

Date: 2025-06-25

## Status

Proposed

## Context

The norm work table needs to show multiple thousand entries. We can not send all of these to the frontend at once.
Therefore, we need some way of sending only some of them at a time. Pagination is a common solution to this problem.

There are two common approaches to this: offset-based pagination and cursor-based pagination.

Cursor-based pagination ensures that no elements are skipped or shown twice if data changes
very often but is more complex to implement. Especially if one wants to support skipping ahead multiple pages. It also
has advantages when dealing with very large datasets as there are more efficient ways of processing cursor-based
pagination than with offset-based pagination.

Our dataset doesn't change often and only consists of some thousand entries. Also jumping to a specific page might be
necessary for at least as long as we do not have a search functionality.

## Decision

We use offset-based pagination.

For setting the offset and the page size we use the query parameters `offset` and `limit`.

## Consequences

We can not use the optimization options of the cursor-based approach.

If we ever have the need to use cursor-based pagination for some other use-case we will have two different pagination
models in our application.

## References

- https://opensource.zalando.com/restful-api-guidelines/#160
