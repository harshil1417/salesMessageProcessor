# README #

This is a simple message processing application which uses in memory Linked blocking queue for demo purpose.
It uses a Factory design pattern to decide the handler for each message based on the message type.
A report of total products sold with their cost will be printed on console after every 10 sales.
Adjustment details will be printed after a threshold(50) sales.

Few test cases are added to test all three different types of sale messages.
NotificationMain java file can also be used to run the application.

# Assumptions #

1. We are assuming it is a single threaded application.
2. All products are measured in terms of number of units. 
3. Messages are of Only 3 types :- Simple, multiple sale and adjustment.

# Future Enhancements #

1. In memory Queue would get replaced with third party message broker.
2. Notification message can be coming from upstream in Pipe or space separated format, So parsing is required to convert it into POJO.
3. We can implement message driven microservices based on a publish/subscribe model using Spring cloud stream and a message broker like Apache Kafka or RabbitMQ. This helps effectively build, scale, run and test our messaging application.
4. Products can be measured in gallons, dozens and other units. So, we can consider using Strategy design pattern to decide the calculation algorithm.
5. Reports can be mailed in excel, PDF formats.
6. We can have daily EOD snapshot of sales done in a relational database. Spring Data rest can be leveraged for implementing the same.
7. Need more test cases to have good code coverage. SONAR and SSAP can be onboaded for code quality checks



