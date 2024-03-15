# OC_DA_JAVA_P9_Note

Note microservice for [medilabo](https://github.com/SimonArduin/OC_DA_JAVA_P9_Medilabo)

This application allows users to interact with a MongoDB database through an API. The database contains notes made by practitioners about the health of their patients.

It is built with Spring as a Maven project, using the following modules :
- Spring Data MongoDB

The application configuration is defined in [application.properties](note/src/main/resources/application.properties). By default, incoming requests are received on port 8001, and the MongoDB database is found on port 27017 with the name medilabo.

***
# CLASS DIAGRAM
![Class Diagram](class_diagram.png)
