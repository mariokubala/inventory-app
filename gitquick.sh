#!/bin/bash
# prepare file:	nano gitquick.sh
# 		chmod a+x gitquick.sh
# usage:	gitquick "Commit message"
# or:		./gitquick "Commit message"
# or in Win:	git add . && git commit -m "xxx" && git push

if [ -z "$1" ]; then
  echo "Usage: $(basename "$0") \"commit message\""
  exit 1
fi

git add .
git commit -m "$1"
git push
