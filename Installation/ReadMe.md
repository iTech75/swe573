System Requirements
-------------------
* ACP Web site and Rest services has been developed using Eclipse Java EE IDE for Web Developers. Version: Mars.1 Release (4.5.1).
* Android application has been developed using Android Studio 1.4.1
* Both application uses Java 1.8.
* For database layer MySql version 5.7.9 has been chosen.
* For Server, Tomcat v.7.0 is prefered.

Installation
------------
* Database structure sql (both structure and data) has been provided in this directory, named 'database_structure.sql'. 
  The URL for this particular file is;
  https://github.com/iTech75/swe573/blob/master/Installation/database_structure.sql
* Database name is 'acp', but you can change it if it matters. You must change that in both 'database_structure.sql' and 
  web application config file (next part).
* ACP web application finds database using the 'https://github.com/iTech75/swe573/blob/master/SourceCode/Web/ACP/src/main/resources/application.properties'
  config file. This file is not provided in github but with the one that has '.example' extension. You can copy and paste this file as 'application.properties'
  and update inside the file with your related info. An example content for this file is below:
  
  >db.driver: com.mysql.jdbc.Driver  
  >db.url: jdbc:mysql://localhost:3306/acp  
  >db.username: dbuser  
  >db.password: 1234password  

* For android part; application has a file name Constants.java, located 'https://github.com/iTech75/swe573/blob/master/SourceCode/Mobile/ACP/app/src/main/java/com/itech75/acp/Constants.java'
  IP address of the web application must be entered here. Constant name is 'SERVICE_ADDRESS'
  
