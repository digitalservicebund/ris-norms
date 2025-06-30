# 19. Pagination for Works

Date: 2025-06-25

## Status

Accepted

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

Spring includes a way to deal with Pagination based on page-size and page-number, this is a kind of offset-based
pagination. The pagination information can directly be passed to the repository. They also include a native way to
load the pagination parameters using query-parameters and to provide the additional information about a page to the
response:

```java
@GetMapping(produces = { APPLICATION_JSON_VALUE })
public ResponseEntity<PagedModel<NormResponseSchema>> getNorm(Pageable pageable) {
  var page = loadNormWorksUseCase.loadNormWorks(new LoadNormWorksUseCase.Options(pageable));
  return ResponseEntity.ok(new PagedModel<>(page.map(NormResponseMapper::fromUseCaseData)));
}
```

This then allows to paginate the request using the query parameters `size` and `page` and creates a response like

```json
{
  "content": [...],
  "page": {
    "size": 20,
    "number": 0,
    "totalElements": 15,
    "totalPages": 1
  }
}
```

Spring's Pageable also allows providing sorting information. We will not use this as that would expose our database
column names to the REST API.

By using the native spring naming for these parameters we keep our project more idiomatic and consistent and ensure that
the meaning of the fields is simple to understand.

## Decision

We use offset-based pagination. We use the native spring representation for a page and for the query parameters.

## Consequences

We can not use the optimization options of the cursor-based approach.

If we ever have the need to use cursor-based pagination for some other use-case we will have two different pagination
models in our application.

## References

- https://opensource.zalando.com/restful-api-guidelines/#160
- https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html#repositories.special-parameters
- https://docs.spring.io/spring-data/jpa/reference/repositories/core-extensions.html#core.web.page
