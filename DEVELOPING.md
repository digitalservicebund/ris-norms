# Development / Tech Notes

Here's what to know from a tech perspective.

# The Modules

Information on the modules can be found in their respective folder's README files:
* [`./time-machine/README.md`](./time-machine/README.md)
* [`./vscode-extension/README.md`](./vscode-extension/README.md)

# The Main Repository

## Architecture Decision Records

[Architecture decisions](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions)
are kept in the `docs/adr` directory.

For adding new records install the 
* [`adr-tools`](https://github.com/npryce/adr-tools) package (e.g. via [`brew`](https://formulae.brew.sh/formula/adr-tools))


See https://github.com/npryce/adr-tools for information on how to use `adr-tools` usage.


## Git Hooks

The repository contains Git hooks which support

* committing only properly formatted source code, not breaking the build
* writing commit messages that follow some convention (wrt. the merits of having a convention , cf. this [article](https://chris.beams.io/posts/git-commit/))
* preventing accidentally pushing secrets and sensitive information

### Git Hooks Setup
In order to make use of the repository's Git hooks, 
* [`Lefthook`](https://github.com/evilmartians/lefthook) 

needs to be installed, which, in turn, makes use of the following CLI tools: 
* [`talisman`](https://thoughtworks.github.io/talisman/docs) - scans for secrets 
* [`gh`](https://github.com/cli/cli) - check CI status (optional)

Once these tools are available, install the hooks via

```bash
lefthook install
```
