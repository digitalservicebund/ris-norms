#!/bin/bash

# NOTE: To create a self-signed RSA key pair, use the following command:
# openssl req -x509 -newkey rsa:2048 -keyout private-key.pem -out certificate.pem -days 365 -nodes

KEY_PATH="${1:-}"

for dir in */; do
  if [ -d "$dir" ]; then
    dir_name=$(basename "$dir")
    zip_file="${dir_name}.zip"
    sig_file="${dir_name}.sig"

    echo "Creating archive: ${zip_file}"
    rm -Rf "$zip_file"
    zip -r -j "$zip_file" $dir

    if [ -n "$KEY_PATH" ] && [ -f "$KEY_PATH" ]; then
      echo "Creating signature: ${sig_file}"
      rm -Rf "$sig_file"
      openssl dgst -sha256 -sign "$KEY_PATH" -out "$sig_file" "$zip_file"
    fi
  fi
done
