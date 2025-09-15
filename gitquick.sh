# File: gitquick.sh
#!/bin/bash
# Usage: gitquick "Commit message"

if [ -z "$1" ]; then
  echo "Usage: $(basename "$0") \"commit message\""
  exit 1
fi

git add .
git commit -m "$1"
git push
