#!/usr/bin/env bash
set -euo pipefail

OUT=${1:-projeto.txt}
ROOT=${2:-.}

files_to_process=()

# Only gather files under src (the user requested "lista tudo do src para frente")
if [ -d "$ROOT/src" ]; then
  while IFS= read -r -d $'\0' f; do
    files_to_process+=("$f")
  done < <(find "$ROOT/src" -type f -print0)
else
  printf "No src directory found under %s\n" "$ROOT" >&2
fi

IFS=$'\n' sorted=($(printf "%s\n" "${files_to_process[@]}" | sort -u))
unset IFS

: > "$OUT"

printf "Project export from %s\n\n" "$(pwd)" >> "$OUT"

for file in "${sorted[@]}"; do
  # Skip unreadable files
  if [ ! -r "$file" ]; then
    printf "SKIPPING unreadable: %s\n" "$file" >&2
    continue
  fi

  # Quick extension-based blacklist for common binary files
  case "$file" in
    *.png|*.PNG|*.jpg|*.JPG|*.jpeg|*.JPEG|*.gif|*.GIF|*.class|*.jar|*.zip|*.tar|*.gz|*.bz2|*.7z|*.exe|*.dll|*.so|*.pdf|*.mp3|*.ogg|*.wav|*.ico)
      printf "SKIPPING BINARY (ext): %s\n" "$file" >&2
      continue
      ;;
  esac

  # Fallback: ensure file is text (grep -Iq returns 0 for binary-safe text check)
  if ! grep -Iq . "$file" 2>/dev/null; then
    printf "SKIPPING BINARY (content): %s\n" "$file" >&2
    continue
  fi

  printf "==== %s ====" "$file" >> "$OUT"
  printf "\n" >> "$OUT"
  cat "$file" >> "$OUT"
  printf "\n\n" >> "$OUT"
done

printf "Export complete: %s (%d files processed)\n" "$OUT" "${#sorted[@]}" >&2
