#!/usr/bin/env bash
export JAVA_HOME=/opt/java/java21
export PATH=$JAVA_HOME/bin:$PATH

set -e
cd "$(dirname "${BASH_SOURCE[0]}")"/..

./gradlew bootRun --args=--spring.profiles.active=prod
