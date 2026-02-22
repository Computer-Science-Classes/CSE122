#!/usr/bin/env bash
set -euo pipefail

echo "[pre-commit] Running Spotless auto-format..."
mvn -q -ntp spotless:apply
git add -A

echo "[pre-commit] Running Checkstype..."
mvn -q -ntp checkstyle:check

if command -v javaWhitelist >/dev/null 2>&1; then
  echo "[pre-commit] Running javaWhitelist..."
  javaWhitelist src/main/java 
else
  echo "[pre-commit] javaWhitelist not found on PATH, skipping."
fi
