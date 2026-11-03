# Theater Management

A Spring Boot REST microservice for managing live theater venues, productions, cast members, performance schedules, and ticket sales.

## Requirements

- Java 25 or newer
- Maven 3.9 or newer
- Optional Kafka broker for ticket-sale events (defaults to `localhost:9092`)

## Run and test

```shell
mvn spring-boot:run
mvn test
```

The application uses an in-memory H2 database by default, so data is reset when it restarts. Configure `DATABASE_URL`, `DATABASE_USERNAME`, and `DATABASE_PASSWORD` to use another database.

All API routes require HTTP Basic authentication. Spring Boot generates a development password and logs it at startup. Set `SPRING_SECURITY_USER_NAME` and `SPRING_SECURITY_USER_PASSWORD` to provide credentials; use TLS or a trusted gateway in production.

Ticket purchases publish `TicketSaleCreatedEvent` to the `theater.ticket-sales` Kafka topic only after the database transaction commits. Configure the broker with `KAFKA_BOOTSTRAP_SERVERS` and the topic with `TICKET_SALES_TOPIC`. If Kafka is unavailable, the ticket sale remains committed and the publishing failure is logged; use a transactional outbox if guaranteed event delivery is required.

## API

All successful create requests return `201 Created` with a `Location` header. Invalid input returns `400`, missing resources return `404`, and business-rule or database-reference conflicts return `409`.

| Method | Route | Purpose |
|---|---|---|
| `GET`, `POST` | `/api/venues` | List or create venues |
| `GET`, `PUT`, `DELETE` | `/api/venues/{id}` | Read, update, or remove a venue |
| `GET`, `POST` | `/api/productions` | List or create productions |
| `GET`, `PUT`, `DELETE` | `/api/productions/{id}` | Read, update, or remove a production |
| `GET`, `POST` | `/api/cast-members` | List or create cast members |
| `GET`, `PUT`, `DELETE` | `/api/cast-members/{id}` | Read, update, or remove a cast member |
| `GET`, `POST` | `/api/productions/{productionId}/cast` | List or assign a cast member to a role |
| `DELETE` | `/api/productions/{productionId}/cast/{assignmentId}` | Remove a role assignment |
| `GET`, `POST` | `/api/performances` | List or schedule a performance |
| `GET` | `/api/performances/{id}` | Read a performance and its remaining capacity |
| `GET`, `POST` | `/api/ticket-sales` | List sales or purchase tickets |
| `GET` | `/api/ticket-sales/{id}` | Read a ticket sale |

Performance schedules must be in the future. Ticket purchases lock the performance row while checking and updating its capacity, preventing concurrent purchases from overselling. A venue's capacity cannot be reduced below tickets already sold for one of its performances.

## Project layout

- `controller` responsibilities are grouped with their domain packages
- `service` logic coordinates business rules and transactions
- Spring Data JPA repositories provide persistence
- Request and response records isolate the REST API from persistence entities
- `common` contains API errors and centralized exception handling
- `security` protects the API; `ticketsale` publishes post-commit Kafka events
