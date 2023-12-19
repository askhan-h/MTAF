#!/bin/bash
# Change directory to where your emulator executable is located
# shellcheck disable=SC2164
cd /Users/userName/Library/Android/sdk/emulator

# Start the emulator with the desired AVD
./emulator -avd "$EMULATOR_NAME"