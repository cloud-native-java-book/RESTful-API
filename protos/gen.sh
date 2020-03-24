#!/bin/bash

SRC_DIR='pwd'
DST_DIR='pwd/../src/main/'

function ensure_implementations() {
  gem list | grep ruby-protocol-buffers || gem install ruby-protocol-buffers
  go get -u github.com/golang/protobuf/{proto,protoc-gen-go}
}

function gen() {
  D=$1
  echo $D
  OUT=$DST_DIR/$D
  mkdir -p $OUT
  protoc -I=$SRC_DIR --${D}_out=$OUT $SRC_DIR/customer.proto
}

ensure_implementations

gen java
gen python
gen ruby
