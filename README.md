# Minjun’s Blog

Personal notes on mathematics, algorithms, and software engineering. Built with Jekyll and the Chirpy theme.

## Language and article workflow

- English is the site’s default and the canonical language for articles.
- Korean and Japanese versions live in `_translations/ko/` and `_translations/ja/`; each translation links to its English article using the same `translation_key`.
- An article’s language switcher shows only translations that exist. It never substitutes machine-translated text or points to a missing page.
- Record article review and translation progress in [`ARTICLE_PROGRESS.md`](ARTICLE_PROGRESS.md). Mark an English revision verified only after checking its technical claims and rendered page.

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

Pull requests and pushes run the `Validate blog` workflow on GitHub-hosted runners; `workflow_dispatch` lets you start one manually. You do not need to build locally for CI or publishing.

Pushing or merging to `main` runs the `Automatic build` workflow in `.github/workflows/pages-deploy.yml`; it builds the production site and publishes it from `gh-pages`. Keep `url` in `_config.yml` as the origin without a trailing slash; Jekyll constructs page URLs from it.

## Adding a translation

1. Give the English post a stable `translation_key`.
2. Add a Markdown document under `_translations/ko/` or `_translations/ja/` with the same key, `lang` set to `ko` or `ja`, and an explicit locale-prefixed `permalink`.
3. Verify the language switcher links both ways and the translated page’s HTML `lang` attribute matches its content.
4. Update the article’s row in `ARTICLE_PROGRESS.md`.

Translations are editorial content and require review; there is no automatic translation fallback.

## License

Blog content and the site theme retain the licenses and attributions declared in their respective files. The site is based on the MIT-licensed [Chirpy Jekyll theme](https://github.com/cotes2020/jekyll-theme-chirpy).
