# 9. Renovate

Date: 2024-05-07

## Status

Accepted

## Context

Keeping libraries up to date is a requirement for
  * running an application securely as vulnerability fixes are applied as soon they become available and for
  * keeping maintenance work low (the later we update, the more work may be needed).

In the past, we have used a combination of 
  * GitHub's [Dependabot](https://github.com/dependabot) that checked our repository and created PRs if it found outdated libraries and
  * a round-robin maintenance shift in the development team that made sure those PRs were handled within a working day

What we noticed after several weeks was that most of the updates did not require human oversight. All that was needed was navigating to the PR and clicking through the GitHub UI to get in merged. At the same time, this required a considerable amount of time the developer could have spent on other tasks. 

Hence we decided to automate that step and as this is an issue many teams face, we quickly found two ways of doing so:
  * Extending our Dependabot setup
  * Using the [MEND Renovate bot](https://docs.renovatebot.com/), a free-of-cost software solution targeted specifically to automate library update workflows on GitHub

Within the dev team we found the following requirements:
  * Nothing must be merged without passing our pipeline
  * Minor and patch versions of libraries should be updated automatically
  * Major updates should not be auto-merged, but be brought to our attention
  * Automatic merges should not happen during the weekend as nobody would respond to failed or broked deployments

Reading the docs on Dependabot, we got the impression that our requirements could be fulfilled, but not very straightforward and it would require changes to our pipeline setup. Basically, it consists of two parts:
  * Enabling the "[Automerge](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/incorporating-changes-from-a-pull-request/automatically-merging-a-pull-request)" feature for our repository and 
  * Making dependabot set that flag on certain conditions by [using the GitHub CLI in our workflow](https://docs.github.com/en/code-security/dependabot/working-with-dependabot/automating-dependabot-with-github-actions#enable-auto-merge-on-a-pull-request)

Renovate on the other hand seemed straightforward to set up, with no changes to our workflows, and it had good documentation on our requirements ([automerge](https://docs.renovatebot.com/configuration-options/#automerge), [restrict to minor/patch](https://docs.renovatebot.com/configuration-options/#matchupdatetypes), [don't run on the weekend](https://docs.renovatebot.com/configuration-options/#schedule)).

Therefore we agreed on the following:
* We'd add renovate to our code repository [`ris-norms`](https://github.com/digitalservicebund/ris-norms), but not to our infrastructure repository.
* We'd set it up as per our requirements
* We'd have it run in parallel to our Dependabot setup for a couple of days and then
* We'd revisit our experiences and decide whether to switch to renovate or stay with Dependabot and try to get automerge working as needed

Roughly 10 days after setting up renovate, our experience was positive both with respect to the setup/configuration and how much it reduced manual interactions in keeping our libraries up to date.

## Decision

In our code repository [`ris-norms`](https://github.com/digitalservicebund/ris-norms)
  * we use [renovate](https://docs.renovatebot.com/) to keep our libraries up to date
  * we turn off [Dependabot](https://github.com/dependabot)
  * our round-robin stays in place in order to handle renovate PRs (if not automerged) in a timely manner

Our [initial setup](https://github.com/digitalservicebund/ris-norms/blob/9c6571b9fe05eee0d6a25acf81e496e99ade1044/renovate.json5) is configured as follows:
  * Automerge is enabled for all of ""minor", "patch", "patch", "pin", "pinDigest" and "digest" updates
  * Automerges only happen on working days
  * Library updates are handled in branches and not PRs by default, i.e. if renovate finds an update, 
    * it creates a branch
    * it waits for the pipeline to pass
    * it merges without creating a PR (details below) so the developers mainly see the updates in the Git log of the repository.
  * PRs will be created by renovate if
    * it's a "major" update 
    * the pipeline did not pass
    * the pipeline "stalled" (i.e. did not complete)

Security implications: 
* We considered our exposure to [supply-chain attacks](https://en.wikipedia.org/wiki/Supply_chain_attack) and came to the conclusion that automerging does not change the threat of this attack vector compared to the current state of updating the libraries manually (what would make a difference is proof-reading the code of the updated libraries). For now, we'll stick with the defensive measures we have (mainly: security scans in the pipeline).


## Consequences

We have found that using renovate 
* greatly reduced the manual effort to keep libraries up to date
* covers a great deal of dependencies/library updates across our stack (e.g. `package.json` files, `package-lock.json` files, `.node-version` configurations, dockerfiles, gradle library definition
 files)

With respect to unintended consequences, we found that defaulting on a rather "aggressive" (i.e. timely) update strategy caused only one problem so far:
* We'e using `.node-version` files to define what version of [`node`](https://nodejs.org/en) to use in our app.
* It now happened that `node` published a new release (`20.13.0`) and renovate automerged that version into our `.node-version` files
* However, the tool we're using to pick up the `.node-version` information, [`nodeenv`](https://github.com/nodenv), did not yet support that version
* Here's how we handled this situation:
    * We [downgraded](https://github.com/digitalservicebund/ris-norms/commit/2ca410b744bec9ab2f4f936282955b1302552807) the node version in the two affected files in order to get back to a state that did not block development (production was not affected). In hindsight, this should have been a plain revert of the renovate commit updating the library.
    * One of the developers subscribed to the `nodeenv` PR on supporting `node 20.13.0`, so we could watch how things unfolded
    * With the next update of node (`20.13.1`), renovate did the update and `nodenv` had been updated in time
