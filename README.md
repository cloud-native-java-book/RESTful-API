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
