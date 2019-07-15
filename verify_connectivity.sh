#!/bin/sh

# Does a /api/role call to the backend
STATUS=$(curl -I -H "Content-Type: application/json" http://`docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' women-can-code-java_wcc-backend_1`:8080/api/role 2> /dev/null | head -n 1 | awk '{print $2}')

if [[ "$STATUS" = "200" ]]
then
    echo "Application status OK"
else
    echo "Application failed to run"
    exit 1
fi