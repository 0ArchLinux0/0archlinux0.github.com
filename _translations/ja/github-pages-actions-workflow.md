---
title: "GitHub ActionsでJekyllサイトを自動ビルド・デプロイする"
author: MINJUN PARK
date: 2026-10-02 10:04:21 +0900
categories: [Guide, GitHub Pages]
tags: [GitHub Actions, Jekyll, GitHub Pages, CI/CD, 自動化, デプロイ]
pin: false
lang: ja
translation_key: github-pages-actions-workflow
permalink: /ja/posts/github-pages-actions-workflow/
---

このリポジトリでは検証と公開を分離しています。作業ブランチのビルド成功はサイト構成が有効であることを示しますが、生成ファイルを `gh-pages` に公開するのは本番ワークフローだけです。

## どの操作でワークフローが動くか

| 操作 | ワークフロー | 結果 |
|---|---|---|
| 任意のブランチへの push、または pull request の作成・更新 | `Validate blog` (`.github/workflows/main.yml`) | サイトをビルドし、言語別ルートを検証します。公開はしません。 |
| Actions → **Validate blog** → **Run workflow** | `Validate blog` | 同じ検証を手動実行します。公開はしません。 |
| サイトに影響する変更を `main` に push またはマージ | `Automatic build` (`.github/workflows/pages-deploy.yml`) | 本番サイトをビルドし、`gh-pages` に公開します。 |
| Actions → **Automatic build** → **Run workflow** で `main` を選択 | `Automatic build` | 本番サイトを手動で再ビルドして公開します。他のブランチはジョブ条件で拒否します。 |

`main` への push では `Validate blog` も実行されます。本番ワークフローは `.gitignore`、`README.md`、`LICENSE`、`ARTICLE_PROGRESS.md` だけを変更するコミットを無視します。この場合も検証は動きますが、デプロイは始まりません。

## 検証内容

`Validate blog` はリポジトリをチェックアウトし、ロックファイルに記録された Ruby 依存関係をインストールしてから `bundle exec jekyll build` を実行します。続いて次のコマンドで生成サイトを検査します。

```sh
ruby tools/verify_language_routes.rb _site
```

ルート検証では、言語モードのページ、canonical URL、言語切替リンクの相互参照、実際に描画されたタイトル、旧 URL からのリダイレクトを確認します。作業ブランチの検証が成功してもサイトは公開されません。公開には `main` への push・マージ、または制限された本番手動実行が必要です。

## 本番公開の流れ

`Automatic build` は全履歴をチェックアウトし、Ruby 2.7 と Bundler を設定した後、`bash tools/deploy.sh` を実行します。このスクリプトは本番 Jekyll ビルドと言語ルート検査を行い、`gh-pages` の内容を生成サイトで置き換えて push します。`gh-pages` はビルド成果物なので直接編集しません。

通常の安全な手順は次のとおりです。

1. 作業ブランチを作り、ソースを編集して push します。
2. `Validate blog` が成功するまで待ち、ビルドとルート検査のステップを確認します。
3. `main` 向け pull request を作成し、PR 検証を通します。
4. PR をマージします。サイトに影響する `main` の変更で `Automatic build` が起動し、公開されます。
5. 本番ワークフローの結果を確認し、英語版と翻訳版の URL で変更ページを開きます。

ソース変更なしで緊急に再ビルドする場合は、**Actions → Automatic build → Run workflow** で `main` を選択します。検証だけを再実行する場合は **Validate blog** を使います。

## ローカルプレビュー

ローカルプレビューは任意です。

```sh
bundle install
bundle exec jekyll serve
```

本番環境だけで再現する問題を調べる場合に限り、本番ビルドを実行します。

```sh
JEKYLL_ENV=production bundle exec jekyll build
```
