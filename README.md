AlfCMIS
=======

Introduction
------------

AlfCMIS is an implementation Java Server Faces to manage documents in [Alfresco](http://www.alfresco.com/) or others [Enterprise Content Management](http://en.wikipedia.org/wiki/Enterprise_content_management) (ECM) that supports [Content Management Interoperability Services](http://en.wikipedia.org/wiki/Content_Management_Interoperability_Services) (CMIS)

## Prerequisite
* GlassFish Server 4.1
* Alfresco 4.2.f or later
* Java 1.8
* Maven 3

## Installation
To compile the source code and create the war package, open your console, move in the folder project where pom.xml is placed and use this command

    mvn package

alfcmis.war will be created in the folder target.
After that, to deploy the war package in GlassFish, move in the folder glassfish/bin and use this command

    asadmin deploy path_project_alfcmis/target/alfcmis.war
