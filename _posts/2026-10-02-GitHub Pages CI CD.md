---
title: "GitHub Actions CI/CD for a Jekyll Site"
author: MINJUN PARK
date: 2026-10-02 10:04:21 +0900
categories: [Guide, GitHub Pages]
tags: [GitHub Actions, Jekyll, GitHub Pages, CI/CD, deployment]
pin: false
lang: en
translation_key: github-pages-actions-workflow
permalink: /posts/github-pages-actions-workflow/
---

This repository separates validation from publication. A feature-branch build proves the site is valid; only a production workflow publishes generated files to `gh-pages`.

## What triggers each workflow?

| Action | Workflow | Result |
|---|---|---|
| Push to any branch or open/update a pull request | `Validate blog` (`.github/workflows/main.yml`) | Builds the site and checks generated language routes; does not publish. |
| Actions → **Validate blog** → **Run workflow** | `Validate blog` | Runs the same validation manually; does not publish. |
| Push or merge to `main` with a site-affecting change | `Automatic build` (`.github/workflows/pages-deploy.yml`) | Builds the production site and publishes it to `gh-pages`. |
| Actions → **Automatic build** → **Run workflow**, with `main` selected | `Automatic build` | Manually rebuilds and publishes production. Other branches are rejected by the job guard. |

A push to `main` also runs `Validate blog`. The production workflow ignores commits that change only `.gitignore`, `README.md`, `LICENSE`, or `ARTICLE_PROGRESS.md`; those commits still run validation but do not trigger deployment.

## What validation checks

`Validate blog` checks out the repository, installs the locked Ruby dependencies, runs `bundle exec jekyll build`, then runs:

```sh
ruby tools/verify_language_routes.rb _site
```

The route checker verifies language-mode pages, canonical article paths, reciprocal language selectors, rendered titles, and legacy redirects. A green feature-branch check is not a publication: deployment still requires a push to `main` or the guarded production dispatch.

## How production publishing works

`Automatic build` checks out full history, sets up Ruby 2.7 and Bundler, then runs `bash tools/deploy.sh`. The script performs a production Jekyll build, runs the language-route checks, replaces the contents of `gh-pages` with the generated site, and pushes that branch. Do not edit `gh-pages` directly; it is generated output.

The safe routine is:

1. Create a feature branch, edit the source files, and push it.
2. Wait for `Validate blog` to pass; review its build and route-check steps.
3. Open a pull request to `main` and wait for its validation check.
4. Merge the pull request. A site-affecting merge to `main` starts `Automatic build` and publishes the site.
5. Check the production workflow result and open the changed page in the English and translated routes.

For an urgent rebuild without a source change, use **Actions → Automatic build → Run workflow** and select `main`. Use **Validate blog** for a check-only run.

## Local preview

A local preview is optional:

```sh
bundle install
bundle exec jekyll serve
```

Use the production build command only when you need to reproduce a production-only issue:

```sh
JEKYLL_ENV=production bundle exec jekyll build
```
