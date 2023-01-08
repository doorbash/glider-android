@echo off

gomobile bind -a -ldflags "-s -w" -target=android -androidapi 19 -o output.aar