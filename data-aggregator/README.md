# Data Aggregator Spring Boot Application

This application demonstrates how to connect to multiple data sources (PostgreSQL, a REST API, and a mocked SDK),
aggregate the data, and store it into a target database (H2 in-memory).

## Prerequisites

*   **Java:** JDK 17 or later (e.g., OpenJDK, Oracle JDK)
*   **Maven:** Apache Maven 3.6.x or later (for building the project)
*   **Docker:** Docker Desktop or Docker Engine (for running the sample PostgreSQL database)

## Setup

1.  **Clone the Repository:**
    ```bash
    # Replace with the actual command if you have a git repo URL
    git clone <your-repository-url>
    cd data-aggregator
    ```

2.  **Run Sample PostgreSQL Database (Source):**
    Open a terminal and run the following Docker command to start a PostgreSQL container.
    This database will act as one of the sources for the aggregation.

    ```bash
    docker run --name sample-postgres -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=sample_source_db -p 5432:5432 -d postgres
    ```
    *   The application is configured to connect to this database on `localhost:5432` with database name `sample_source_db`, user `user`, and password `password`.
    *   On first run with the application, if the `source_data` table is empty, a `CommandLineRunner` will attempt to insert a few sample records.

3.  **Other Data Sources:**
    *   **REST API (JSONPlaceholder):** This is a public API and requires no special setup. The application will fetch user data from `https://jsonplaceholder.typicode.com/users`.
    *   **SDK (Mocked):** The SDK interaction is mocked within the application and requires no external setup.
    *   **Target Database (H2 In-Memory):** The target database where aggregated data is stored is an H2 in-memory database. It's created and destroyed with the application lifecycle and requires no setup.

## Running the Application

1.  **Build and Run:**
    Navigate to the project root directory (`data-aggregator`) in your terminal and run:

    ```bash
    mvn spring-boot:run
    ```
    The application will start, typically on port `8080`.

## Triggering Data Aggregation

Once the application is running:

1.  **Via Web Browser:**
    Open your web browser and go to:
    `http://localhost:8080/api/v1/trigger-aggregation`

2.  **Via `curl` (Terminal):**
    ```bash
    curl http://localhost:8080/api/v1/trigger-aggregation
    ```

    You should receive a message like "Data aggregation process triggered and completed successfully."
    Check the application console logs for more details about the data fetched and saved.

## Verifying the Output (in H2 Console)

The aggregated data is stored in an H2 in-memory database. You can inspect its contents using the H2 Console.

1.  **Access H2 Console:**
    Open your web browser and go to:
    `http://localhost:8080/h2-console`

2.  **Connect to the Database:**
    *   **JDBC URL:** `jdbc:h2:mem:targetdb` (This should be pre-filled)
    *   **User Name:** `sa`
    *   **Password:** `password`
    *   Click **Connect**.

3.  **Query the Data:**
    Once connected, you can run SQL queries. For example, to see the contents of the aggregated data table:

    ```sql
    SELECT * FROM AGGREGATED_DATA;
    ```
    This table (`AGGREGATED_DATA`) corresponds to the `TargetData` entity.

## Project Structure

(Optional: Briefly describe key packages if desired, but the above is the priority)
*   `src/main/java/com/example/dataaggregator/`
    *   `config/`: Datasource configurations, Initializers.
    *   `controller/`: Spring MVC controllers (e.g., for triggering aggregation).
    *   `model/`: JPA Entities (source, target, rest, sdk).
    *   `repository/`: Spring Data JPA repositories.
    *   `service/`: Business logic services (data fetching, orchestration).
*   `src/main/resources/application.properties`: Application configuration.
*   `pom.xml`: Maven project configuration.
