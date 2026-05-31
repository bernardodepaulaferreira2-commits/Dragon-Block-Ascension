#!/usr/bin/env bash
set -euo pipefail

OUT=${1:-project_export.txt}
ROOT=${2:-.}

files_to_process=()

if [ -d "$ROOT/src" ]; then
  while IFS= read -r -d $'\0' f; do
    files_to_process+=("$f")
  done < <(find "$ROOT/src" -type f -print0)
fi

if [ -d "$ROOT/gradle" ]; then
  while IFS= read -r -d $'\0' f; do
    files_to_process+=("$f")
  done < <(find "$ROOT/gradle" -type f -print0)
fi

for name in build.gradle gradle.properties gradlew gradlew.bat settings.gradle README.md LICENSE fabric.mod.json; do
  if [ -f "$ROOT/$name" ]; then
    files_to_process+=("$ROOT/$name")
  fi
done

IFS=$'\n' sorted=($(printf "%s\n" "${files_to_process[@]}" | sort -u))
unset IFS

: > "$OUT"

printf "Project export from %s\n\n" "$(pwd)" >> "$OUT"

for file in "${sorted[@]}"; do
  case "$file" in
    *.png|*.PNG)
      printf "SKIPPING PNG: %s\n" "$file" >&2
      continue
      ;;
  esac

  if [ ! -r "$file" ]; then
    printf "SKIPPING unreadable: %s\n" "$file" >&2
    continue
  fi

  if grep -Iq . "$file" 2>/dev/null; then
    printf "==== %s ====" "$file" >> "$OUT"
    printf "\n" >> "$OUT"
    cat "$file" >> "$OUT"
    printf "\n\n" >> "$OUT"
  else
    printf "SKIPPING BINARY: %s\n" "$file" >&2
  fi
done

printf "Export complete: %s (%d files processed)\n" "$OUT" "${#sorted[@]}" >&2
