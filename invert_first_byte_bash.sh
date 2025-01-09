#!/bin/bash

# Check if a filename is provided as an argument
if [ -z "$1" ]; then
  echo "Usage: $0 <filename>"
  exit 1
fi

file="$1"

# Check if the file exists
if [ ! -f "$file" ]; then
  echo "Error: File '$file' not found."
  exit 1
fi

# Extract the first byte of the file in hexadecimal format
byte=$(dd if="$file" bs=1 count=1 2> /dev/null | xxd -p)

# Invert the byte
inverted_byte=$(printf "%02X" $((255 - 0x$byte)))

# Write the inverted byte back to the file
printf "\\x$inverted_byte" | dd of="$file" bs=1 count=1 seek=0 conv=notrunc 2> /dev/null

# Modify filename extension
if [[ "$file" == *.invByte1 ]]; then
  # Remove the extension
  mv "$file" "${file%.invByte1}"
else
  # Add the extension
  mv "$file" "$file.invByte1"
fi

echo "File processed successfully."
