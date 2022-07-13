#!/bin/bash

DEBUG=false
RESPONSE=""
ACCESS_TOKEN=""

BOLD='\e[1m'
FAIL='\e[5;31m'
SUCCESS='\e[5;32m'
RED='\e[0;31m'
YELLOW='\e[;33m'
NC='\e[0m'

ensure_dependency() {
  if ! which "$1" &>/dev/null ; then
    echo -e "${RED}$1 not found${NC}"
    exit 1
  fi
}

ensure_dependency curl
ensure_dependency jq

print_header() {
  if [ ! -z "$1" ]; then
    echo -e "${BOLD}${1}${NC}"
    echo ""
  fi
}

print_request() {
  if [ ! -z "$1" ]; then
    echo -e "${BOLD}REQUEST:${NC}"
    echo -e "${YELLOW}${1}${NC}"
    echo ""
  fi
}

print_separator() {
  echo ""
  echo "---"
  echo ""
}

handle_fail() {
  echo -e "${BOLD}RESPONSE:${NC}"
  echo $RESPONSE
#  { echo $RESPONSE | jq } || echo $RESPONSE
  echo ""
  echo -e "${FAIL}=> FAIL${NC}"
  exit 1;
}

handle_success() {
  echo -e "${BOLD}RESPONSE:${NC}"
  echo $RESPONSE
#  { echo $RESPONSE | jq } || echo $RESPONSE
  echo ""
  echo -e "${SUCCESS}=> SUCCESS${NC}"
}

debug() {
  echo -e "${BOLD}DEBUG:${NC}"
  echo -e "${RED}${@}${NC}"
  echo ""
}

print_header "Login to the UAA with a \"client_credentials\" grant"
print_request "curl -s -X POST --data \"grant_type=client_credentials\" http://internal:internal@localhost:9999/oauth/token"

RESPONSE=$(curl -s -X POST --data "grant_type=client_credentials" http://internal:internal@localhost:9999/oauth/token)
ACCESS_TOKEN=$(echo $RESPONSE | jq .access_token | xargs)
[ "$DEBUG" = "true" ] && debug $RESPONSE $ACCESS_TOKEN
if [ "$ACCESS_TOKEN" = "null" ]; then
  handle_fail
else
  handle_success
fi

print_separator

print_header "Login to the UAA with a \"password\" grant"
print_request "curl -s -X POST --data \"username=user&password=user&grant_type=password&scope=openid\" http://web_app:changeit@localhost:9999/oauth/token${NC}"

RESPONSE=$(curl -s -X POST --data "username=user&password=user&grant_type=password&scope=openid" http://web_app:changeit@localhost:9999/oauth/token)
ACCESS_TOKEN=$(echo $RESPONSE | jq .access_token | xargs)
[ "$DEBUG" = "true" ] && debug $RESPONSE $ACCESS_TOKEN
if [ "$ACCESS_TOKEN" = "null" ]; then
  handle_fail
else
  handle_success
fi

print_separator

print_header "Accessing protected resource with the access_token"
print_request "curl -sSf -H \"Authorization: bearer ${ACCESS_TOKEN}\" http://localhost:8081/api/demo1"

RESPONSE=$(curl -sSf -H "Authorization: bearer ${ACCESS_TOKEN}" http://localhost:8081/api/demo1)
[ "$DEBUG" = "true" ] && debug $RESPONSE
if [[ "$RESPONSE" =~ "error" ]]; then
  handle_fail
else
  handle_success
fi

print_separator

print_header "check_token: Ask UAA whether the provided access_token is valid or not"
print_request "curl -sSf -X POST http://internal:internal@localhost:9999/oauth/check_token?token=${ACCESS_TOKEN}"

RESPONSE=$(curl -sSf -X POST http://internal:internal@localhost:9999/oauth/check_token?token=${ACCESS_TOKEN})
[ "$DEBUG" = "true" ] && debug $RESPONSE
if [[ "$RESPONSE" =~ "error" ]]; then
  handle_fail
else
  handle_success
fi

print_separator

print_header "token_key: A public key which is used to validate a JWT signature"
print_request "curl -sSf http://localhost:9999/oauth/token_key"

RESPONSE=$(curl -sSf http://localhost:9999/oauth/token_key)
[ "$DEBUG" = "true" ] && debug $RESPONSE
if [[ "$RESPONSE" =~ "error" ]]; then
  handle_fail
else
  handle_success
fi

print_separator

print_header "jwks: The JSON Web Key Set (JWKS) is a set of keys containing the public keys used to verify any JSON Web Token (JWT) issued by the authorization server and signed using the RS256 signing algorithm"
print_request "curl -sSf http://localhost:9999/.well-known/jwks.json"

RESPONSE=$(curl -sSf http://localhost:9999/.well-known/jwks.json)
[ "$DEBUG" = "true" ] && debug $RESPONSE
if [[ "$RESPONSE" =~ "error" ]]; then
  handle_fail
else
  handle_success
fi
