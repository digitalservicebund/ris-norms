# 4. Java in the backend (not Kotlin)

Date: 2024-01-23

## Status

Accepted

## Context

When the RIS Norms team decided to take a new approach on their task, the question whether to use Java or Kotlin in the backend / on the server came up.

As it is not be the Norms team, but the IT department of the [Federal Office of Justice](https://www.bundesjustizamt.de/EN/Home/Home_node.html) (DE: Bundesamt für Justiz, BfJ) that will maintain the solution in the long run; any decision does have to work for them as well.

More specifically, the BfJ-IT understands the Federal Guidelines on Architecture (DE: "[Architekturrichtlinien des Bundes](https://www.cio.bund.de/Webs/CIO/DE/digitaler-wandel/Achitekturen_und_Standards/IT_Architektur_Bund/IT_Architektur_Bund-node.html)") to exclude Kotlin.

The team's preference was Kotlin, and its understanding of the guidelines is that Kotlin would match the requirements.

No agreement could be found between the parties and eventually the DigitalService's Product Management on the NeuRIS project weighted in with a focus on unblocking the project's proceedings.

## Decision

The team will use Java in the backend for this project. Already existing Kotlin code will be migrated or replaced.

## Consequences

* Long-term maintenance by the BfJ-IT is possible.
* The team will devise ways of working effectively in Java
