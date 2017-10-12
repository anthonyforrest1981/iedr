#!/bin/bash
     for fl in *.php; do
     mv $fl $fl.old
     sed 's/-1.6/-1.7/g' $fl.old > $fl
     rm -f $fl.old
     done
