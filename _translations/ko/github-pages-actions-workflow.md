---
title: "GitHub Actions로 Jekyll 사이트 자동 빌드·배포하기"
author: MINJUN PARK
date: 2026-10-02 10:04:21 +0900
categories: [Guide, GitHub Pages]
tags: [GitHub Actions, Jekyll, GitHub Pages, CI/CD, 자동화, 배포]
pin: false
lang: ko
translation_key: github-pages-actions-workflow
permalink: /ko/posts/github-pages-actions-workflow/
---

이 저장소는 검증과 게시를 분리한다. 기능 브랜치에서 빌드가 성공하면 사이트 구성이 유효하다는 뜻이고, 생성된 파일을 `gh-pages`에 게시하는 작업은 프로덕션 워크플로만 수행한다.

## 어떤 작업이 워크플로를 실행하나?

| 작업 | 워크플로 | 결과 |
|---|---|---|
| 어느 브랜치에든 푸시하거나 PR을 열거나 갱신 | `Validate blog` (`.github/workflows/main.yml`) | 사이트를 빌드하고 언어별 경로를 검사한다. 게시하지 않는다. |
| Actions → **Validate blog** → **Run workflow** | `Validate blog` | 같은 검증을 수동으로 실행한다. 게시하지 않는다. |
| 사이트에 영향을 주는 변경을 `main`에 푸시하거나 병합 | `Automatic build` (`.github/workflows/pages-deploy.yml`) | 프로덕션 사이트를 빌드해 `gh-pages`에 게시한다. |
| Actions → **Automatic build** → **Run workflow**에서 `main` 선택 | `Automatic build` | 프로덕션을 수동으로 다시 빌드하고 게시한다. 다른 브랜치는 작업 조건에서 차단된다. |

`main` 푸시는 `Validate blog`도 실행한다. 프로덕션 워크플로는 `.gitignore`, `README.md`, `LICENSE`, `ARTICLE_PROGRESS.md`만 바뀐 커밋을 무시한다. 이 경우 검증은 실행되지만 배포는 시작하지 않는다.

## 검증 범위

`Validate blog`는 저장소를 체크아웃하고 잠금 파일에 맞는 Ruby 의존성을 설치한 뒤 `bundle exec jekyll build`를 실행한다. 이어 다음 명령으로 생성된 사이트를 검사한다.

```sh
ruby tools/verify_language_routes.rb _site
```

경로 검사기는 언어 모드 페이지, canonical 경로, 언어 선택기의 양방향 링크, 실제 제목, 이전 경로 리다이렉트를 확인한다. 기능 브랜치에서 검증이 통과해도 사이트가 게시된 것은 아니다. 게시하려면 `main`에 푸시·병합하거나 제한된 프로덕션 수동 실행을 해야 한다.

## 프로덕션 게시 과정

`Automatic build`는 전체 Git 이력을 체크아웃하고 Ruby 2.7과 Bundler를 준비한 뒤 `bash tools/deploy.sh`를 실행한다. 스크립트는 프로덕션 Jekyll 빌드와 언어 경로 검사를 수행하고, `gh-pages`의 내용을 생성된 사이트로 교체한 다음 푸시한다. `gh-pages`는 빌드 결과물이므로 직접 수정하지 않는다.

안전한 일반 작업 순서는 다음과 같다.

1. 기능 브랜치를 만들고 원본 파일을 수정해 푸시한다.
2. `Validate blog`가 통과할 때까지 기다리고 빌드 및 경로 검사 단계를 확인한다.
3. `main` 대상 PR을 열고 PR 검증을 통과시킨다.
4. PR을 병합한다. 사이트에 영향을 주는 `main` 변경은 `Automatic build`를 실행해 게시한다.
5. 프로덕션 워크플로 결과를 확인하고 영어 및 번역 경로에서 변경 페이지를 연다.

원본 변경 없이 긴급 재빌드할 때는 **Actions → Automatic build → Run workflow**에서 `main`을 선택한다. 검사만 다시 할 때는 **Validate blog**를 사용한다.

## 로컬 미리보기

로컬 미리보기는 선택 사항이다.

```sh
bundle install
bundle exec jekyll serve
```

프로덕션에서만 재현되는 문제를 조사할 때만 프로덕션 빌드를 실행한다.

```sh
JEKYLL_ENV=production bundle exec jekyll build
```
