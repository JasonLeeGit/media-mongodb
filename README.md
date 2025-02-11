# media-mongodb
Spring Boot Media Service WIth Docker MongoDB database

# To Run 

Import existing gradle project into Eclipse

Build gradle / Eclipse Project -> Clean

Start MongoDB: open terminal and cd to src\main\resources and enter, docker-compose up -d

Start Application: In Eclipse right click MediaApplication.java and select Run As Spring Boot App

Open browse: http://localhost:8989/swagger-ui/index.html

Sample data available in src\main\resources\swagger addAllMedia, In swagger select MongoDB Update Media Controller and paste json and execute.

![image](https://github.com/user-attachments/assets/0157d3e3-6496-41bb-8e8d-bbd0696112b3)

