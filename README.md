# RESTful APIs:
This is an Implementation of the Cloud Native Java Book - Chapter 6.
best RESTful apis tends to exploit more HTTP's capabilities.

#### Leonard Richardson Maturity Model:

* Level 0: Describes APIs that uses HTTP as a transport protocol and nothing more.
* Level 1: Describes APIs that use multiple URIs to distinguish related nouns. but otherwise avoid
the full features of HTTP.
* Level 2: Describes APIs that leverage transport-native properties (LIKE HTTP verbs, status codes)
to enhance the service. NOTE: of you do everything incorrectly with Spring MVC you still end up with
an API that's Level 2 compliant.
* Level 3: Describes APIs that require no a prior knowledge of a service in order to navigate it -
HATEOAS.


#### Content Negotiation:
Content Negotiation is one of the most powerful features of HTTP: the same service may serve clients
that accept different protocols.

#### Little Bite of Servlet Container Terminology:
In a typical Servlet Container implementation (Like Apache tomcat, ...) each http request is handled
via a thread that is obtained from that Servlet Container's thread pool.

Threads pool are necessary to control the amount of the threads that are being executed simultaneously.

``` 
In a regular basis, each thread consumes ~1MB of memory just to allocate a single thread stack, 
so 1000 simultaneous requests could use ~1GB of memory only for the thread stacks. Therefore, 
thread pool comes as a solution to limit the amount of threads being executed, fitting the application
to a scenario where it doesn’t throw an OutOfMemoryError.
```

#### Error Handling:
@ControllerAdvie is a special type of component that may introduce behavior (and respond to exceptions) for
any number of controllers. They are natural place to stick centralized @ExceptionHandler handlers.


### Useful Resources:
* [Using Asynchronuous Calls With Spring MVC](https://adrianobrito.github.io/2018/01/11/using-callable-responses-in-spring-mvc/)
<!-- * [the difference between Callable and WebAsyncTask in Spring MVC]() -->
