#!/usr/bin/env bash
# verify.sh — Run all quality gates for the AndroidSDD project.
#
# Usage:
#   bash scripts/verify.sh
#
# Gates:
#   1. Unit tests (JVM) with coverage verification (>= 80% business logic)
#   2. Instrumented/Compose UI tests (requires connected device or emulator)

set -e

REPO_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$REPO_ROOT"

echo "=== AndroidSDD Verification ==="
echo "Working directory: $REPO_ROOT"
echo ""

echo "--- Gate 1: Unit tests + coverage ---"
./gradlew test koverVerify
echo "Unit tests PASSED."
echo ""

echo "--- Gate 2: Instrumented / Compose UI tests ---"
./gradlew connectedAndroidTest
echo "Instrumented tests PASSED."
echo ""

echo "=== All gates PASSED ==="

