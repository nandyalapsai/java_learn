# Complete progressive roadmap to master **Java Spring** (from Spring Core → microservices → production-ready)

Nice — you want a practical, job-ready path. Below is a progressive, hands-on roadmap that starts at Spring Core and walks you to advanced microservices, observability, DevOps and real-world projects. Each **stage** gives: what to learn, which tools & libraries to use, concrete exercises & project ideas, and what to show in your portfolio or resume for that stage.

---

## Quick note

I assume you already know **Java SE** fundamentals (OOP, collections, exceptions, threads, streams, basic I/O) — if not, start there first. This roadmap focuses on Spring-specific knowledge and practical engineering patterns.

---

# Stage 0 — Foundation (prereqs & environment)

**Goal:** set up your environment, build-tool fluency, and Java foundations you'll use everywhere.

**Concepts**

* Java 8+ features commonly used with Spring: lambdas, Streams, Optionals, method references, CompletableFuture basics.
* SOLID principles, design patterns (Factory, Singleton, Repository, Service, Strategy), layers (controller/service/repository).
* HTTP basics (verbs, status codes, headers), JSON, async vs sync.

**Tools & libraries**

* JDK 17+ (LTS) or latest LTS; IntelliJ IDEA (Community/Ultimate), VS Code (optional).
* Build tools: **Maven** and **Gradle** (know both basics).
* Git, GitHub/GitLab/Bitbucket.
* Postman / HTTPie / curl for API testing.
* Docker (basic), Docker Compose.

**Hands-on tasks**

* Create a small Maven and Gradle Java project from scratch.
* Host a Git repo with a README and basic `.gitignore`.
* Practice writing small CLI utilities that read/write JSON.

**Portfolio outcome**

* GitHub repo: simple Java projects, clear README and build instructions, Dockerfile for an app.

---

# Stage 1 — Spring Core & Dependency Injection (DI)

**Goal:** understand Spring container, beans, lifecycle, and DI — the foundation of everything Spring.

**Concepts**

* IoC container, beans, bean scopes (singleton, prototype, request, session).
* Dependency Injection types: constructor vs setter vs field injection (favour constructor).
* Component scanning, `@Component`, `@Service`, `@Repository`, `@Controller`, `@Configuration`, `@Bean`.
* `@Autowired`, `@Qualifier`, `@Primary`.
* Bean lifecycle callbacks: `InitializingBean`, `DisposableBean`, `@PostConstruct`, `@PreDestroy`.
* Profiles (`@Profile`) and property resolution.

**Tools & libraries**

* Spring Framework (core) — use via Spring Boot starter later but learn basics.
* Lombok (to reduce boilerplate, optional but common).
* JUnit 5 + Mockito for small unit tests.

**Hands-on tasks**

* Build a plain Spring application (without Boot if possible) that wires services and repositories.
* Create a demo “calculator” service injected into a CLI runner.
* Write unit tests for beans using Mockito.

**Portfolio outcome**

* Repo showing DI patterns, config classes, unit tests that validate wiring.

---

# Stage 2 — Spring Boot fundamentals

**Goal:** use Spring Boot to accelerate development, configure apps, and package runnable services.

**Concepts**

* Spring Boot starters, `spring-boot-starter-*`.
* Auto-configuration, `@SpringBootApplication`, application properties (`application.yml` vs `.properties`).
* Profiles and property overrides, external configuration loading order.
* Actuator basics (health, metrics).
* Packaging: fat JAR, Dockerizing a Spring Boot app.
* CommandLineRunner and ApplicationRunner.

**Tools & libraries**

* Spring Boot (latest compatible with JDK).
* spring-boot-starter-actuator, spring-boot-starter-test.
* Maven/Gradle plugin for Spring Boot.
* Docker (Dockerfile + multi-stage builds).

**Hands-on tasks**

* Create a Spring Boot REST app with a couple endpoints and Actuator.
* Add `application-dev.yml` and `application-prod.yml` and demo switching profiles.
* Dockerize the app and run with Docker Compose.

**Portfolio outcome**

* A small boot app with README: show how to build, run, switch profiles, Docker run.

---

# Stage 3 — Spring MVC & REST API design

**Goal:** build maintainable HTTP APIs using Spring MVC and best REST practices.

**Concepts**

* `@Controller`, `@RestController`, `@RequestMapping`, `@GetMapping` etc.
* Request/Response bodies, `@RequestBody`, `@RequestParam`, `@PathVariable`.
* Content negotiation (JSON), HTTP status codes, error handling.
* `@ExceptionHandler`, `@ControllerAdvice` for global error handling.
* DTO/Entity separation, DTO mapping patterns (MapStruct / ModelMapper).
* Validation: `javax.validation` / Hibernate Validator (`@Valid`, `@NotNull`, `@Size`).
* Pagination & sorting (Spring Data's `Pageable`).
* API documentation: OpenAPI/Swagger (springdoc-openapi).

**Tools & libraries**

* spring-boot-starter-web, springdoc-openapi-ui, MapStruct, Lombok.
* Jackson config (custom serializers/deserializers), Gson optional.

**Hands-on tasks**

* Build a **Bookstore API**: CRUD for books + searching, paging, sorting.
* Implement validation and global error mapping.
* Add API docs (Swagger UI) and include sample API contract in repo.

**Portfolio outcome**

* Clean REST API repo with DTOs, mapping, validation, Swagger UI, Postman collection.

---

# Stage 4 — Data access: JDBC, JPA, Spring Data JPA

**Goal:** persist data reliably, design schema, use JPA and Spring Data for productivity.

**Concepts**

* JDBC basics, connection pooling, HikariCP.
* JPA fundamentals: Entities, relationships (OneToMany, ManyToOne, ManyToMany), lazy vs eager.
* Spring Data JPA: repositories, derived queries, `@Query`, projections.
* Transactions: `@Transactional`, propagation, isolation levels.
* Migrations: Flyway / Liquibase.
* Handling N+1 queries, DTO projections, batch operations, optimistic & pessimistic locking.

**Tools & libraries**

* spring-boot-starter-data-jpa, Hibernate (JPA provider), HikariCP.
* Flyway or Liquibase, PostgreSQL / MySQL (practice with both).
* Testcontainers (for DB in tests).

**Hands-on tasks**

* Extend Bookstore to use persistent DB with JPA; create relationships (Author, Publisher).
* Add Flyway migrations and seed data.
* Write integration tests with Testcontainers for DB operations.
* Demonstrate handling N+1 with fetch joins and DTO projection.

**Portfolio outcome**

* Repo with DB migrations, sample schema, integration tests that run locally via Docker/Testcontainers.

---

# Stage 5 — Spring Security (authentication & authorization)

**Goal:** secure APIs and services, implement auth flows used in real jobs.

**Concepts**

* Basics: stateless vs stateful auth, sessions, cookies.
* Spring Security core: filters, `SecurityFilterChain`, `HttpSecurity` config.
* Form authentication vs REST token auth.
* JWT based auth (signing, expiry, refresh tokens), storing tokens securely.
* OAuth2 / OIDC basics; Spring Security OAuth2 Client and Resource Server.
* Role-based & method-level security (`@PreAuthorize`, `@RolesAllowed`).
* Password storage (BCrypt), CORS configuration, CSRF for browser apps.
* Integration with identity providers (Keycloak, Auth0) — basic flow.

**Tools & libraries**

* spring-boot-starter-security, jjwt or Nimbus JOSE + JWT, Keycloak (local Docker for practice).

**Hands-on tasks**

* Add JWT authentication to the Bookstore app: login, register, refresh tokens, role-based endpoints.
* Integrate with a local Keycloak instance to protect an endpoint with OAuth2.
* Secure a Thymeleaf-based UI (if you implement web UI).

**Portfolio outcome**

* Secure API sample with README showing token flows, sample JWTs, Postman collection for auth.

---

# Stage 6 — Testing, Quality & CI

**Goal:** build confidence via automated tests and CI pipelines.

**Concepts**

* Unit testing (JUnit 5, Mockito), integration tests (`@SpringBootTest`, MockMvc), slice tests (`@DataJpaTest`, `@WebMvcTest`).
* Testcontainers for DB, Kafka, Redis in tests.
* API testing: RestAssured, WireMock for stubbing external HTTP services.
* Contract testing (Pact), end-to-end smoke tests.
* Static analysis and code quality: SonarQube, Checkstyle, SpotBugs, PMD.
* Code coverage fundamentals (Jacoco).
* CI basics: run tests and builds on push, artifact publishing.

**Tools & libraries**

* JUnit 5, Mockito, Spring Boot Test, Testcontainers, RestAssured, WireMock.
* GitHub Actions / GitLab CI / Jenkins for pipelines.
* SonarCloud / SonarQube, Jacoco.

**Hands-on tasks**

* Add unit + integration tests to your app; ensure tests run in CI (GitHub Actions).
* Create a CI workflow that runs lint, tests, build, and publishes a Docker image to a registry.

**Portfolio outcome**

* CI workflow file in repo, tests with good coverage, Testcontainers-based integration tests.

---

# Stage 7 — Caching, Performance & Resiliency

**Goal:** speed up services and make them resilient under failure.

**Concepts**

* Caching strategies (in-memory vs distributed): `@Cacheable`, cache invalidation.
* Redis basics and Spring Cache abstraction with Redis.
* Connection pooling, DB query tuning, indexing basics.
* Rate limiting and throttling (Bucket4j, API gateway).
* Circuit breakers and bulkheads: Resilience4j (fallbacks, retries, timeouts).
* Idempotency, retries with exponential backoff.
* Asynchronous processing: `@Async`, thread pools, `CompletableFuture`.

**Tools & libraries**

* Spring Cache, Redis (Docker), Resilience4j, HikariCP.
* JProfiler / async-profiler (optional), Gatling/JMeter for load testing.

**Hands-on tasks**

* Add Redis caching for expensive endpoints and show performance improvement.
* Add Resilience4j with retry and circuit-breaker around an external call.
* Run a basic load test showing caching and circuit-breaker effects.

**Portfolio outcome**

* Documentation in repo showing performance metrics before/after caching or resiliency features.

---

# Stage 8 — Messaging & Event-driven patterns

**Goal:** build decoupled systems using messaging (background jobs, events).

**Concepts**

* Message brokers: RabbitMQ, Apache Kafka fundamentals.
* Spring AMQP (Rabbit) and Spring for Apache Kafka / Spring Cloud Stream.
* Event-driven patterns: pub/sub, event sourcing basics, CQRS core idea.
* Exactly-once & at-least-once delivery semantics; idempotency design.
* Consumer groups, partitions (Kafka), dead-letter queues (DLQ).

**Tools & libraries**

* RabbitMQ, Kafka (local Docker), Spring Cloud Stream, Kafka Streams (optional).

**Hands-on tasks**

* Implement an email/notification microservice consuming events from an orders topic.
* Build a simple event-driven flow: Order service publishes `OrderPlaced` → Inventory service updates stock → Notification service sends email.
* Demonstrate DLQ handling for failed messages.

**Portfolio outcome**

* Event-driven example repo with docker-compose, schema for events, and logs showing flows.

---

# Stage 9 — Spring Cloud & Microservices

**Goal:** design, build, and operate distributed Spring-based microservices.

**Concepts**

* Monolith → microservices tradeoffs; decomposition strategies.
* Service Discovery (Eureka) or DNS-based discovery.
* Centralized configuration: Spring Cloud Config (or external config like Consul).
* API Gateway: Spring Cloud Gateway / Zuul basics (routing, filters, rate-limiting).
* Inter-service communication: REST vs OpenFeign vs gRPC.
* Circuit breakers & resilience patterns applied to microservices (Resilience4j).
* Distributed tracing (Spring Cloud Sleuth + Zipkin / Jaeger).
* Load balancing: Ribbon (legacy) vs Spring Cloud LoadBalancer, client vs server-side.
* Service meshes basics (Istio) — conceptual.
* Distributed transactions & saga pattern (choreography vs orchestration).

**Tools & libraries**

* Spring Cloud components (Eureka, Config, Gateway, OpenFeign), Resilience4j, Spring Cloud Sleuth + Zipkin/Jaeger.
* Docker Compose / Kubernetes for environment orchestration.
* PostgreSQL, Redis, Kafka across services.

**Hands-on tasks (capstone microservices project)**

* Build an **E-commerce microservices system**:

  * Services: `user-service`, `catalog-service`, `order-service`, `inventory-service`, `payment-service`, `notification-service`.
  * Add Eureka (service registry) or use Kubernetes DNS.
  * Centralized config server with environment-specific properties.
  * API Gateway with routing, authentication JWT validation and rate limiting.
  * Use OpenFeign for inter-service calls; protect them with Resilience4j.
  * Event-driven consistency using Kafka (OrderPlaced events).
  * Add distributed tracing (Sleuth + Zipkin/Jaeger) and Prometheus metrics for each service.
  * Deploy services with Docker Compose and optionally to a Kubernetes cluster.

**Portfolio outcome**

* Multi-repo or monorepo microservices project with architecture diagram, deployment scripts, API gateway, centralized config, tracing screenshots, and a README walkthrough.

---

# Stage 10 — Observability, Monitoring & Production readiness

**Goal:** make services observable, deployable, and operable in production.

**Concepts**

* Logging: structured logs (JSON), log correlation IDs, SLF4J + Logback configuration.
* Metrics: Micrometer, Prometheus scrape, Grafana dashboards.
* Tracing: distributed traces, spans, correlation IDs.
* Health checks (Actuator), readiness & liveness probes.
* Security hardening: secrets management, TLS basics.
* Deployment strategies: blue/green, canary, rolling updates.
* Backup/DR and backup strategies for DBs.

**Tools & libraries**

* Micrometer, Prometheus, Grafana, ELK stack (Elasticsearch + Logstash + Kibana) or EFK, Zipkin/Jaeger.
* Kubernetes (k8s) for deployments; Helm for packaging.
* Vault (or cloud KMS) for secrets (conceptual/practical).

**Hands-on tasks**

* Add Micrometer + Prometheus and create a Grafana dashboard.
* Configure structured logs and show trace/log correlation for a request traversing services.
* Configure Actuator endpoints and secure them.
* Deploy the microservices to a k8s cluster (minikube or kind) and demonstrate rolling updates.

**Portfolio outcome**

* Screenshots or dashboards, `k8s` manifests, helm charts, and a short video demo showing tracing & metrics.

---

# Stage 11 — Advanced topics / optional but valuable

**Goal:** expand to cutting-edge topics valued in advanced roles.

**Concepts & techs**

* Reactive Spring: WebFlux, reactive data (R2DBC), when to use reactive vs blocking.
* GraphQL with Spring (spring-graphql).
* gRPC services for high-throughput inter-service calls.
* CQRS and Event Sourcing (Axon Framework or custom implementation).
* Serverless Spring (Spring Cloud Function) and Function-as-a-Service patterns.
* Performance tuning at JVM level: GC tuning, JIT, memory leaks.
* Multi-region/distributed systems design.

**Hands-on tasks**

* Build a small reactive API with WebFlux and R2DBC.
* Create a GraphQL layer (products query/mutation) over an existing service.
* Implement a simple saga for distributed transaction orchestration.

**Portfolio outcome**

* One advanced demo (reactive or GraphQL) showcasing new skills.

---

# Real-world project ideas (progressive complexity)

Use these as capstone projects; each maps to many stages above.

1. **Personal Bookstore (Beginner → Intermediate)**

   * CRUD API, validation, paging, Swagger, JPA persistence, tests.
   * Add JWT auth, user roles, book ratings.

2. **Blog platform (Intermediate)**

   * Posts, comments, tagging, search (Elasticsearch), image upload, admin panel (Thymeleaf).
   * Moderation workflow, scheduled cleanup jobs.

3. **Task Management App (Intermediate)**

   * REST + web UI, real-time updates via WebSocket/Stomp, scheduled reminders, file attachments.

4. **E-commerce Monolith → Microservices (Advanced capstone)**

   * Start as monolith; split into microservices (user, catalog, order, inventory, payment).
   * API Gateway, Centralized config, service discovery, Kafka events, distributed tracing.
   * Deploy to k8s with Helm; add CI/CD pipelines, monitoring and blue/green strategy.

5. **Ride-hailing mini-system (Advanced)**

   * Real-time features (WebSocket), geospatial search, scaled architecture, resilience and failover.

6. **Banking/payments demo (Advanced + Security focus)**

   * High security, idempotency, transactional integrity (saga), encryption of sensitive data, PCI-lite compliance reasoning.

---

# Best practices checklist (what employers look for)

* Use constructor injection everywhere, avoid field injection.
* Keep controllers thin; business logic in services; DB logic in repositories.
* DTOs for public API; never expose JPA entities directly.
* Use `@Transactional` carefully and at service-layer boundaries.
* Proper error handling and consistent API error format.
* Write unit tests + integration tests; CI must run them.
* Use Flyway/Liquibase for migrations; never rely on Hibernate `ddl-auto` in production.
* Use centralized logging, structured logs, correlation IDs across services.
* Secure secrets and credentials; never commit them to Git.
* Use OpenAPI/Swagger; maintain API contracts.
* Document system architecture, runbook, and README with steps to run locally.

---

# How to practice effectively (daily/iterative engineering habits)

* Code small feature → write tests → run locally in Docker → commit → CI → push.
* Do code reviews (even self review via PR template).
* Reproduce real incidents locally: kill a service, simulate latency, see how circuit-breaker behaves.
* Read production issues from open-source repos to learn debugging.
* Build one end-to-end project and harden it (security, tracing, monitoring) — employers love “productionized” demos.

---

# Portfolio & interview checklist (what to include & prepare)

**Repo checklist**

* Clear README with architecture diagram and endpoints.
* Dockerfile + docker-compose for local run.
* CI workflow file.
* Database migrations scripts.
* OpenAPI/Swagger docs & Postman collection.
* Tests (unit + integration) and test coverage report.
* Screenshots or links for dashboards & tracing.

**Interview prep topics**

* Explain DI and how Spring implements it.
* Differences between Spring MVC and WebFlux.
* How `@Transactional` works; propagation behaviors and common pitfalls.
* How to secure REST APIs with JWT and OAuth2.
* Explain CAP theorem, eventual consistency, saga vs 2PC.
* How to prevent N+1 problems in JPA.
* Show a code sample: demonstrate implementing a REST endpoint + service + repository + unit test.
* Be ready to architect an app on a whiteboard — show components, data flow, failure modes, and monitoring.

---

# Final tips & next steps

1. **Project-first learning**: pick one small app (Bookstore) and finish it end-to-end before expanding features. Then evolve it into the microservices capstone.
2. **Incremental complexity**: add security, then CI, then caching, then messaging — each addition teaches new production skills.
3. **Document everything**: your README should read like a mini case study — problem, architecture, decisions, tradeoffs, how to run, known limitations.
4. **Practice interviews**: code whiteboard, system-design questions, and debugging sessions.
5. **Show, don’t only tell**: deploy one project to a cloud (Heroku, DigitalOcean App Platform, or AWS/GCP) or at least show Docker Compose/k8s manifests with logs/metrics.

---
