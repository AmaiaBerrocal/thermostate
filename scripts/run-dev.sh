#!/usr/bin/env bash

set -e
cd "$(dirname "${BASH_SOURCE[0]}")"/..

./gradlew bootRun --args=--spring.profiles.active=dev
