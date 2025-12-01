# COMP 3005 - Database Management Systems
## Health and Fitness Club Management System

## Technologies Used
- Java 21+
- Spring 3.5.7
- Hibernate
- PostgreSQL 18
- Docker 4.50.0

## ORM Integration Bonus
This project fully integrates Spring Boot, Hibernate, Jakarta and Liquibase as a full-fledged application & ORM system.

All entities are under the "src/main/java/../model" folder tagged with Jakarta annotations to denote database properties such as @Id, @GeneratedValue, @ManyToOne, @OneToMany, @OneToOne. These annotations automatically assign PK/FK, Cascading rules, Loading behaviour, Table/Column mappings, etc.

All database access is done through repository interfaces such as "AccountRepository", "SessionRepository", "RoomRepository", etc. They are all located under /repository src folder.
Most simple CRUD operations are automatically handled and do not need explicit methods, such as sessionRepo.findById(sessionId).

Service layer manipulates ORM Managed Entity Objects, not direct SQL queries. This prevents ORM bypass. The only notable ORM "bypass" is the searchMembersByName function as there's no clean way to do it.

Major entities mapped using ORM:
- Accont
- Availability
- Equipment
- EquipmentIssue
- EquipmentRepair
- Goal
- HealthFocus
- Metric
- MetricEntry
- Room
- RoomBooking
- Session

## License
This project is for academic use only (course submission). No warranty. Not for commercial distribution.
