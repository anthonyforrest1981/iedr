#!/bin/bash

echo 'Installing displaytag-doc'
mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file -Dfile=<path_to_displaytag_directory>/displaytag-doc.pom.xml -DpomFile=<path_to_displaytag_directory>/displaytag-doc.pom.xml -Dpackaging=pom -DlocalRepositoryPath=<path_to_local_maven_repo>
echo 'Installing displaytag'
mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file -Dfile=<path_to_displaytag_directory>/displaytag-2.0-SNAPSHOT.jar -DpomFile=<path_to_displaytag_directory>/displaytag.pom.xml -DlocalRepositoryPath=<path_to_local_maven_repo>
echo 'Done'

