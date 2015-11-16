#!/bin/sh

# usage ./free-merge.sh UPSTREAM_BRANCH_NAME
# example: ./free-merge.sh origin/lg-master

# make sure we are in the root of the project
cd $(dirname "$(readlink -f "$BASH_SOURCE")")

# merge, but do not commit
echo "merging with upstream $@..."
git merge --no-commit $@

# files that are not used by Free Looking Glass
IGNORE_FILES=("nonfree" \
  "lookingglass/collection-modules" \
  "lookingglass/lookingglass-tests")

# remove the ignored files from git
echo ""
echo "removing ignored files: ${IGNORE_FILES[@]}..."
for dir in "${IGNORE_FILES[@]}"; do
  git rm --ignore-unmatch --force -r $dir
done

# remind the user that they must commit to complete this merge, if a merge happened.
echo ""
echo "when finished resolving conflicts run \`git commit\` if necessary."

