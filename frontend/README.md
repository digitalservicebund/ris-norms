# frontend

The frontend is the main entry point for users of _RIS norms_.

From here the users can handle their procedures ("Vorgänge").

# Development

## Prerequisites

* Node.js (with a `.node-version` file for simplified setup using [`nodenv``](https://github.com/nodenv/nodenv)

## Setup
* `npm i` will fetch all dependencies

## Start locally
* `npm run dev` will start the application. By default on [local port 5173](http://localhost:5173)

## Testing
* `npm run test` will run `jest` tests

## Style Checks
* `npm run style:check` will run linting and formatting
* `npm run style:fix` will try to fix linting and formatting issues