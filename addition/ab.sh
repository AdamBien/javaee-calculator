#!/bin/sh
ab -p src/test/resources/input.json -T"application/json" -c 10 -n 100 http://localhost:8080/addition/resources/addition/
