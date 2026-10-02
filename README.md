# Minjun’s Blog

Personal notes on mathematics, algorithms, and software engineering. Built with Jekyll and the Chirpy theme.

## Language and article workflow

- English is the default mode at `/`; Korean and Japanese have separate mode pages at `/ko/` and `/ja/`.
- The persistent language selector links to the matching article when a reviewed translation exists; otherwise it opens that language’s article index. Home navigation stays in the selected mode.
- The English home page lists canonical source posts; Korean and Japanese indexes list only reviewed translations. There is no runtime machine translation.
- Translations live in `_translations/ko/` and `_translations/ja/` with the same stable `translation_key` as the English article.
- Record article review and translation progress in [`ARTICLE_PROGRESS.md`](ARTICLE_PROGRESS.md). Mark a language version verified only after checking its technical claims and rendered page.

## Local development

Requirements: Ruby and Bundler compatible with the versions in `Gemfile.lock`.

```sh
bundle install
bundle exec jekyll serve
```

Preview at <http://127.0.0.1:4000/>. A production build is optional locally; GitHub Actions builds the site for validation and publishing:

```sh
JEKYLL_ENV=production bundle exec jekyll build
```

## Publishing

`Validate blog` runs for every push (any branch), every pull request, and its own `workflow_dispatch`. It builds the site and checks generated language/article routes; it never publishes.

`Automatic build` publishes the production site to `gh-pages`:

| Action | Workflow result |
|---|---|
| Push to a feature branch or open/update a pull request | `Validate blog` only |
| Actions → **Validate blog** → **Run workflow** | Manual validation only |
| Push or merge a site change to `main` | `Validate blog` plus `Automatic build` and production publish |
| Actions → **Automatic build** → **Run workflow**, with `main` selected | Manual production build and publish; other branches are blocked |

The production workflow ignores commits that change only `.gitignore`, `README.md`, `LICENSE`, or `ARTICLE_PROGRESS.md`; validation still runs, but those changes do not deploy. `Automatic build` runs `bash tools/deploy.sh`, which production-builds the Jekyll site, checks language routes, and replaces the generated contents of `gh-pages`. Do not edit `gh-pages` directly. Keep `url` in `_config.yml` as the origin without a trailing slash.

## Adding a translation

1. Give the English post a stable `translation_key`.
2. Add a Markdown document under `_translations/ko/` or `_translations/ja/` with the same key, `lang` set to `ko` or `ja`, and an explicit locale-prefixed `permalink`.
3. Verify the language switcher links both ways and the translated page’s HTML `lang` attribute matches its content.
4. Update the article’s row in `ARTICLE_PROGRESS.md`.

Keep English post titles English-only; put bilingual English–Korean titles in the Korean translation's own `title` field.

Translations are editorial content and require review; there is no automatic translation fallback.

## License

Blog content and the site theme retain the licenses and attributions declared in their respective files. The site is based on the MIT-licensed [Chirpy Jekyll theme](https://github.com/cotes2020/jekyll-theme-chirpy).
