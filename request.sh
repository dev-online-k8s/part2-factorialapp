#!/bin/bash

while true; do    
  count=$((RANDOM % 50))

  for ((i = 0; i < count; i++)); do
    curl "http://localhost/factorial?n=1" &
  done
 
  sleep 1  
done

