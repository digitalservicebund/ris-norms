# 18. Unpublished ("working-copy") Norm Manifestations

**Date**: 2025-04-04

## Status

Accepted

## Context

We can not change a published manifestations of a norm. Therefore, we need an unpublished manifestation when we want to
change something in an existing expression of a norm.
After publishing the `point-in-time-manifestation` field of the eli of a manifestation must be the date of the publishing.
As not everything is published every day we need to update the `point-in-time-manifestation` part of the manifestation eli
during the publishing process. If we want to do further edits of that expression after (queuing for) publishing we need a new
unpublished manifestation of that expression.

Previously we created a new manifestation when entering the textko page. We used the current date for the
`point-in-time-manifestation`. This then created problems when continuing editing after having published that
manifestation. We therefore created a new manifestation with tomorrow's date during the publishing to enable further
editing. This stopped us from publishing the manifestation a second time that day (or more precisely overwriting the
manifestation that will be published that night).

There is no reason for multiple unpublished manifestations to exists as only the newest manifestation will ever be
shown.

The highest possible value for the `point-in-time-manifestation` is `2999-12-31`.

## Decision

- We use `2999-12-31` as a general `point-in-time-manifestation` date for all unpublished manifestations.
- We set the correct `point-in-time-manifestation` date during the publishing process.

## Consequences

- An unpublished manifestation is always the newest manifestation.
- We can queue a manifestation again at the same date (overwriting the previously queued manifestation).
- We do not need to think about the `point-in-time-manifestation` field (and having multiple manifestations) outside the
  publishing workflows.
- There is always at most one unpublished manifestation for every expression.
- We could enforce that only manifestations with `point-in-time-manifestation` equal to `2999-12-31` can be edited by
  users and that no manifestation with that date is copied to the portal buckets. Thus adding additional checks that
  only stuff that has gone through the publishing workflow gets published.
