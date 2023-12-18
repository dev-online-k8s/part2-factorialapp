#!/bin/bash

n=1000
while true; do
  n=$((n + 1))
  echo "$n!"
  curl "http://localhost/factorial/$n?key=abcd-1234-5678"
  echo
  sleep 0.5
done
