#!/bin/bash

echo "This program will attempt to unzip, install and setup checker-framework"
echo
echo "That means:"
echo "1. unpacking checker-framework to /opt/checkerframework/"
echo "2. exporting CHECKER_HOME variable by adding /etc/profile.d/checker.sh file with appropriate export"
echo
echo "You *MUST* begin by downloading checker-framework-1.8.11.zip from smb://rdfs/nadanownik/Projects/IEDR/checker-framework-1.8.11.zip and placing it in this folder"
echo
read -r -p "Do you wish to continue? [y/N] " response
response=${response,,}    # tolower
if [[ $response =~ ^(yes|y)$ ]]; then
  if [ ! -d checkerframework ]; then
    echo "Unziping checker-framework"
    if [ ! -f checker-framework-1.8.11.zip ]; then
      echo "No checker-framework-1.8.11.zip present, aborting"
      exit 1
    fi
    unzip -q checker-framework-1.8.11.zip
    mv checker-framework-1.8.11 checkerframework
    rm checker-framework-1.8.11.zip
  else
    echo "checkerframework directory already exists, assuming it's a previously unziped one and reusing it"
  fi

  if [ ! -w /opt -a ! -w /etc/profile.d ]; then
    echo "Looks like /opt or /etc/profile.d are not writable by you. Please recheck your permissions"
    echo "Aborting"
    exit 1
  fi

  echo "Moving checker-framework to /opt"
  if [ -d /opt/checkerframework ]; then
    echo "There already exists a folder /opt/checkerframework. Removing it first"
    rm -rf /opt/checkerframework
  fi
  mv -f checkerframework /opt/


  echo "Setting up CHECKER_HOME variable"
  echo "export CHECKER_HOME=/opt/checkerframework/checker" > /etc/profile.d/checker.sh

  echo "Remember to source /etc/profile.d/checker.sh to have the variable set up in existing session"
  echo "Done. Bye."
else
  echo "OK, I won't touch your system. Bye."
  exit 0
fi

