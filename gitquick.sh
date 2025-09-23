#!/bin/bash
# prepare file:	nano gitquick.sh
# 		chmod a+x gitquick.sh
# usage Cmder:	gitquick.sh "Commit message"
# or git-bash:	./gitquick "Commit message"
# or Win-cmd:	git add . && git commit -m "Commit message" && git push

if [ -z "$1" ]; then
  echo "Usage: ./$(basename "$0") \"commit message\""
  exit 1
fi

git add .
git commit -m "$1"
git push
