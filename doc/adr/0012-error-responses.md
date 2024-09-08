# 12. Error responses

Date: 2024-08-20

## Status

Accepted

## Context

We want to show error messages in the frontend. For this the backend needs to provide more information to the frontend
than the status code of the response.

It is also possible that multiple validation errors occur at the same time. In that case we want to be able to send all
of them to the frontend and include which part of the request the validation error is applicable to.

The purpose of this ADR is similar to these ADRs of the caselaw project:

- https://github.com/digitalservicebund/ris-backend-service/blob/main/doc/adr/0014-error-handling-concept.md
- https://github.com/digitalservicebund/ris-backend-service/blob/main/doc/adr/0017-addressing-of-validation-errors.md

[RFC-9457 (Problem Details for HTTP APIs)](https://www.rfc-editor.org/rfc/rfc9457) addresses how such information can be
sent by an API.

## Decision

We follow [RFC-9457 (Problem Details for HTTP APIs)](https://www.rfc-editor.org/rfc/rfc9457) for our error responses.
All error responses are JSON objects.

The `type` field always starts with `/errors/`. Therefore, all errors are URLs relative to our application.

The `instance` field is filled with the api call URI that triggered the exception.

Both `title` and `details` are written in english.

Only the `type` field is required. All other fields are optional.

In general at least `type`, `instance` and `title` should be filled out to help with debugging. Also, all additional
information that is required for writing a good error message should be provided as additional fields (extension
members). For this it might be helpful to write a `details` message and provide all variables used in it as additional
fields.

### Multiple error messages in one response

For errors that can have multiple messages (e.g. after a validation) we mostly follow the example from Section 3 of
RFC-9457 for `HTTP/1.1 422 Unprocessable Content`.

The multiple errors MUST be of the same general type. Otherwise, only the most severe error is reported. E.g. it is
possible to send multiple `norm-not-valid` errors but not a `norm-not-valid` error at the same time as
a `norm-not-found` error. In this case only the `norm-not-found` error will be sent.

The response includes an additional `errors` array. The elements of the `errors` array can have the same fields as the
global object. If no `type` is provided it is assumed that it is the same as the parent type.

### Extension Members (additional fields)

We expect certain extension members to be needed by many errors. Therefore, here are some predefined extension members
that can be used when needed and may not be used for other purposes:

- `pointer`: a JSON-Pointer ([RFC-6901](https://www.rfc-editor.org/info/rfc6901)) referencing the field of the JSON
  object responsible for this error
- `eid`: an eId (LegalDocML.de) referencing the part of the norm responsible for the error
- `guid`: a GUID (LegalDocML.de) referencing the part of the norm responsible for the error

### Displaying messages for the user

To create messages to display to the user the `type` must be used in combination with the other fields. The `title`
and `details` may not be parsed or used for the translation.

### Examples

Example error response for NormNotFound:

```
{
 "type": "/errors/norm-not-found",
 "title": "Norm not found.",
 "details": "The requested norm (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1) could not be found.",
 "instance": "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"
 "eli": "/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
}
```

Example error response for validation:

```
{
 "type": "/errors/norm-not-valid",
 "title": "Norm validation failed.",
 "instance": "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/mods/hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_rref-1"
 "errors": [{
    "type": "/errors/norm-not-valid/quoted-structure/target-and-up-to-are-not-siblings"
    "title": "Target node und upTo node are not siblings",
    "details": "Target node with eid hauptteil-1_buch-2_kapitel-1_para-3_abs-2 and target upTo node with eid hauptteil-1_buch-2_kapitel-1_para-3_abs-3 are not siblings in ZF0 norm with eli eli/bund/bgbl-1/1002/1/2024-03-05/1/deu/regelungstext-1.",
    "eid": "hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_rref-1",
    "hrefEid": "hauptteil-1_buch-2_kapitel-1_para-3_abs-2"
    "upToEid": "hauptteil-1_buch-2_kapitel-1_para-3_abs-3",
    "zf0Eli": "eli/bund/bgbl-1/1002/1/2024-03-05/1/deu/regelungstext-1"
 }, {
    "type": "/errors/norm-not-valid/textual-mod/destination-href-empty",
    "title": "Destination Href is empty",
    "eid": "meta-1_analysis-1_activemod-1_textualmod-1"
 }]
}
```

Example error response for an invalid json object:

```
{
 "type": "/errors/proprietary-frame-schema-not-valid",
 "title": "ProprietaryFrameSchema not valid.",
 "errors": [{
    "pointer": "/fna",
    "type": "/errors/proprietary-frame-schema-not-valid/fna-does-not-exist",
    "title": "FNA does not exist.",
    "details": "FNA \"48\" does not exist.",
    "instance": "uri/to/api/call"
 }, {
    "pointer": "/ressort",
    "type": "/errors/proprietary-frame-schema-not-valid/ressort-does-not-exist",
    "title": "Ressort does not exist.",
    "details": "Ressort \"Bundesministerium für GIFs und Memes\" does not exist.",
    "instance": "uri/to/api/call"
 }]
}
```

Examples for translating of the validation errors into german:

```
/errors/norm-not-valid: Norm-Validierung fehlgeschlagen.
/errors/norm-not-valid/textual-mod/destination-href-empty: destinationHref der activeModification ({eid}) ist leer
/errors/norm-not-valid/quoted-structure/target-and-up-to-are-not-siblings: Die Ziel Elemente (href: {hrefEid} und upTo: {upToEid}) der quotedStructure {eid} sind keine Geschwister in der ZF0-Norm {zf0Eli}
```

## Consequences

* We need to define error types and messages for all errors in the backend
* We need to add a translation file and a i18n-library to our frontend code that maps error codes to translated strings.
* We need to adapt the backend API code to return the correct response.
* We need to adapt the frontend to deal with the new API response.
