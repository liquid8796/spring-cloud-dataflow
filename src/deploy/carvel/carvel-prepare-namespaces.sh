#!/usr/bin/env bash
if [ -z "$BASH_VERSION" ]; then
    echo "This script requires Bash. Use: bash $0 $*"
    exit 1
fi
SCDIR=$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")

function check_env() {
    eval ev='$'$1
    if [ "$ev" == "" ]; then
        echo "env var $1 not defined"
        exit 1
    fi
}
function count_kind() {
    jq --arg kind $1 --arg name $2 '.items | .[] | select(.kind == $kind) | .metadata | select(.name == $name) | .name' | grep -c -F "$2"
}

function create_secret() {
    SCRT_NAME=$1
    REG_NAME=$2
    REG_USER=$3
    REG_PWD=$4
    SCRT_NS=$5
    echo "Create docker-registry secret $SCRT_NAME for $REG_NAME username=$REG_USER"
    kubectl create secret docker-registry "$SCRT_NAME" \
        --docker-server="$REG_NAME" \
        --docker-username="$REG_USER" \
        --docker-password="$REG_PWD" \
        --namespace "$SCRT_NS"
    # echo "Annotating $SCRT_NAME for image-pull-secret"
    # kubectl annotate secret "$SCRT_NAME" --namespace "$SCRT_NS"  secretgen.carvel.dev/image-pull-secret=""
}
function patch_serviceaccount() {
    if [ "$2" != "" ]; then
      kubectl patch serviceaccount $2 -p "$1" --namespace "$NS"
    fi
    kubectl patch serviceaccount default -p "$1" --namespace "$NS"
}
if [ "$2" = "" ]; then
  echo "Usage $0: <namespace> <serviceaccount>"
  exit 1
fi
NS=$1
SA=$2

kubectl create namespace $NS
$SCDIR/add-roles.sh "system:aggregate-to-edit" "system:aggregate-to-admin" "system:aggregate-to-view"
PRESENT=$(kubectl get serviceaccount --namespace $NS --output=json | count_kind serviceaccount "$SA")
if ((PRESENT==0)); then
    kubectl create serviceaccount "$SA" --namespace $NS
fi
IMPORT=true
if [ "$IMPORT" == "true" ]; then
  PRESENT=$(kubectl get namespace --output=json | count_kind namespace "secret-ns")
  if ((PRESENT == 0)); then
    kubectl create namespace secrets-ns
  fi
  $SCDIR/carvel-add-registry-secret.sh scdfmetadata index.docker.io "$DOCKER_HUB_USERNAME" "$DOCKER_HUB_PASSWORD"
  $SCDIR/carvel-import-secret.sh scdfmetadata $NS
  $SCDIR/carvel-add-registry-secret.sh reg-creds-dockerhub index.docker.io "$DOCKER_HUB_USERNAME" "$DOCKER_HUB_PASSWORD"
  $SCDIR/carvel-import-secret.sh reg-creds-dockerhub $NS
else
  create_secret scdfmetadata index.docker.io "$DOCKER_HUB_USERNAME" "$DOCKER_HUB_PASSWORD" "$NS"
  create_secret reg-creds-dockerhub index.docker.io "$DOCKER_HUB_USERNAME" "$DOCKER_HUB_PASSWORD" "$NS"
fi

patch_serviceaccount '{"imagePullSecrets": [{"name": "reg-creds-dockerhub"},{"name":"scdfmetadata"}]}' $SA

if [ "$SCDF_TYPE" = "pro" ]; then
    if [ "$TANZU_DOCKER_USERNAME" = "" ]; then
        echo "Cannot find TANZU_DOCKER_USERNAME"
        exit 1
    else
        check_env TANZU_DOCKER_PASSWORD
        # for production
        # $SCDIR/carvel-add-registry-secret.sh reg-creds-dev-registry registry.packages.broadcom.com "$TANZU_DOCKER_USERNAME" "$TANZU_DOCKER_PASSWORD"
        $SCDIR/carvel-add-registry-secret.sh reg-creds-dev-registry spring-scdf-docker-virtual.usw1.packages.broadcom.com "$TANZU_DOCKER_USERNAME" "$TANZU_DOCKER_PASSWORD"
        $SCDIR/carvel-import-secret.sh reg-creds-dev-registry $NS
        patch_serviceaccount '{"imagePullSecrets": [{"name": "reg-creds-dockerhub"},{"name":"scdfmetadata"},{"name": "reg-creds-dev-registry"}]}' $SA
    fi
fi
