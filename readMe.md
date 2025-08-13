mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=5000"
mvn clean compile
mvn clean package

After the above command successfully runs,
Open Postman and test all API

--Task


1. Call the apis from html files and create front end to show timer
STart, STop, Status are POST API, Status is Get API

POST http://localhost:8080/api/stopwatch/start
POST http://localhost:8080/api/stopwatch/stop
POST http://localhost:8080/api/stopwatch/reset
GET http://localhost:8080/api/stopwatch/status

END Goal: When the project starts, the user should see the page on Chrome. with Start, stop button, reset button and the functionality should work as per APIs above. The status or duration should be shown on the web page whenn the user clicks stop button.

2. Understand what is the difference between Post API and Get API. 
    2.1 When POST OR GET will be used. 
    3.1 Real time examples.

3. What other kind of APIs are available 
    3.1 When they will be used ?
    3.2 how they will be used ?
    3.3 Real tiem examples