# MOTIVATION & IDEA: WHAT I ACHIEVED HERE?

Traditionally, web applications managed user sessions by generating a unique SessionID for each user session, storing it on the server, and then requiring that it be sent with each client request.

This approach, though simple, came with a significant overhead: For every single request, the server had to query the database to validate the SessionID, creating a bottleneck for performance and scalability.

As developers sought more efficient methods, JWT (JSON Web Tokens) gained popularity. JWTs encode the user's information directly into the token, allowing stateless authentication. This meant no database lookups for each request, seemingly a silver bullet for the inefficiencies of traditional session management.

Nevertheless, the initial enthusiasm for JWTs was soon tempered by the realization of their drawbacks by the security experts:

+ JWTs are prone to XSS and XSRF attacks.
+ As JWTs are self-contained, server-side cannot invalidate a token once issued and it remains active until expiration. This means that true logout and true blocking might never happen until the token is expired.
+ In sophisticated applications, there is often a requirement to keep a bigger data in JWT, possibly leading to a huge payload.

So, there must be a solution: without having to do time-consuming database lookups while still having the best of the security.

# SOLUTION: SPRING SESSIONS REDIS!

In this regard, I revisited the SessionID approach but with a twist—by leveraging a persistent and high-speed Redis server in between.

Redis, an in-memory data structure store, known for its low-latency operations, provides a central session store that is much faster than traditional databases. By implementing Spring Session Redis, we bring the following advantages to our application:

+ Latency Reduction: With Redis, session lookups are incredibly fast, eliminating the latency issue of database queries.
+ Horizontal Scalability: Spring's default session management, tied to the Tomcat server's memory, is inherently non-scalable. Redis allows us to externalize session storage, so no matter which server instance handles the request, the session can be retrieved and validated.
+ Stateful Security: Unlike JWTs, session information can be revoked or modified server-side, allowing for immediate logouts and changes to session validity.

In this project I made an implementation of this logic and I am planning to use it in my following real-world projects. 

Thanks for reading!

# FURTHER REFERENCE

[[JSON Web Tokens (JWT) are Dangerous for User Sessions]([URL](https://redis.com/blog/json-web-tokens-jwt-are-dangerous-for-user-sessions/)https://redis.com/blog/json-web-tokens-jwt-are-dangerous-for-user-sessions/)]
