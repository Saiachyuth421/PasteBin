To run the App locally we just need to follow these steps :
   (a) Clone the repository using the command "git clone https://github.com/Saiachyuth421/PasteBin.git"
   (b) Build the project using the command "./mvnw clean package"
   (c) Run the application using the command "java -jar target/*.jar"
   (d) Test endpoints via Postman or browser :
       Health check: http://localhost:8080/api/healthz
       Create paste (POST JSON to /api/pastes)
       View paste (GET /p/<id>)


 Persistence Layer used :
    (a) H2 in-memory database is used for simplicity.
    (b) Can be swapped with MySQL/PostgreSQL if required.
    (c) Stores all pastes, TTL, max views, and current view count.


 Important Design Decisions:
   (a) TTL and max views are enforced on each fetch; pastes become unavailable once either constraint triggers.
   (b) HTML content is safely escaped to prevent script injection.
   (c) Deterministic testing supported via the x-test-now-ms header.
   (d) Built using Spring Boot + Java 21 + Maven for quick local and serverless deployment.
