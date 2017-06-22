# HttpEchoServer
[Oogway] Create a simple multithreaded Http echo server

Write a minimal multi-threaded HTTP echo server that accepts connections in a main thread, hands connections to an available worker thread from a thread pool, parses and processes the POST request in the worker thread, ignores path specified in the POST, and sends a 200 OK response with the same content-type header and payload from the request. In other words, echo back the content to the client.


 
#### Project done in NetBeans IDE (Windows).

Download NetBeans from : https://netbeans.org/downloads/

Setting up the project:
-----------------------
1. Import the entire project in your NetBeans IDE and build and run it.
2. Clone the Client Program for testing the working of the HttpEchoServer.
https://github.com/Talina06/HttpTestClient.git


Test Scenarios:
---------------

Test Scenario 1: *Testing multi-threaded execution and blocking of accept call when all threads are busy.*

1. Configuration: src/httpechoserver/Config.java
  * maxThreadCount = 1 //i.e maximum one thread processes a single request at a time in the ThreadPool.
  * backlogCount = 2 //i.e 2 requests are waiting while the ThreadPool is full.
2. Start around 10 instances of the HttpTestClient program.
3. The server will process one request at a time, and put 2 requests in the pendingQueue.
4. Each time a thread is available, the server pops a request from the pendingQueue and pushes the next incoming request onto the pendingQueue.
5. While the above is happening, the next incoming requests are not accepted by the server, until queue has space available.


Test Scenarios 2: *Testing Clean Server Termination.*
1. Same configuration as Test 1.
  * shutDownTime = 10 Seconds. // i.e: time limit for all threads to shutdown gracefully.
2. Add a System.exit() call after processing one request in the WorkerThread class.
3. Start around 3-4 instances of the HttpTestClient program.
4. When the server processes the first request, it exits due to the System.exit() call.
5. This invoked the shutdownhook and the server proceeds to close all active/waiting threads in the ThreadPool.
6. Then, the Server Socket is closed.
