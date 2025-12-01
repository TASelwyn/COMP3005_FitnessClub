# COMP 3005 - Final Project
## Health and Fitness Club Management System
This project fully integrates Spring Boot, Hibernate, Jakarta and Liquibase as a full-fledged application & ORM system.

## Project Structure
```bash
src/main/
   - java/               # Program Root
      .../fitnessclub/   # Spring Code
         - controller/   # REST Endpoints 
         - dto/          # Data Transfer Objects (FE <-> BE)
         - exception/    # REST Exception handler
         - model/        # ORM Entity mappings
         - repository/   # ORM Repos Interfaces (to talk to db)
         - security/     # Spring Security (auth, jwt)
         - service/      # Business Logic
   - resources/          # Program Setup
     - db.changelog/     # Database Seed
     - application.yml   # Spring Boot configuration
compose.yaml             # Docker compose file
mvnw                     # Maven Runtime
```
## Technologies Used

### Backend
- Java 21
- Spring Boot 3.5.7
- Hibernate ORM 6.6.1
- Liquibase 4.30.0
- PostgreSQL 18.1
- Docker 4.50.0
### Black magic
- Lombok 1.18.42
- Jakarta Persistence 3.1.0
- SpringDoc OPENAPI 2.6.0

## Running the project
Clone the repository
```bash
git clone git@github.com:TASelwyn/COMP3005_FitnessClub.git
```

Enter the project folder
```bash
cd COMP3005_FitnessClub
```

Build & Run the application
```bash
./mvnw spring-boot:run
```

## ORM Integration Bonus
All entities are under the "src/main/java/../model" folder tagged with Jakarta annotations to denote database properties such as @Id, @GeneratedValue, @ManyToOne, @OneToMany, @OneToOne. These annotations automatically assign PK/FK, Cascading rules, Loading behaviour, Table/Column mappings, etc.

All database access is done through repository interfaces such as "AccountRepository", "SessionRepository", "RoomRepository", etc. They are all located under /repository src folder.
Most simple CRUD operations are automatically handled and do not need explicit methods, such as sessionRepo.findById(sessionId).

Service layer manipulates ORM Managed Entity Objects, not direct SQL queries. This prevents ORM bypass. The only notable ORM "bypass" is the searchMembersByName function as there's no clean way to do it.

Code sample for updating an entity:
```
Account acc = accRepo.findById(accountId);
acc.setFirstName(firstName);
acc.setLastName(lastName);
accRepo.save(acc);
```

Major entities mapped using ORM:
- Account
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

## Authors
- Thomas Selwyn
- Nitish Grover

## License
This project is for academic use only (course submission). No warranty. Not for commercial distribution.
