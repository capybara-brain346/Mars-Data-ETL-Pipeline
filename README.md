# Mars Weather Data Project
![MarsETL](https://github.com/user-attachments/assets/45151816-4433-45c1-b911-b79f99344406)

## Overview

The Mars Weather Data Project is a Java application that retrieves and stores weather data from NASA's InSight Mars weather API into a MongoDB database. The application fetches weather data, processes it, and allows retrieval of stored documents for further analysis. It is designed to facilitate the exploration of Martian weather conditions.

## Features

- Connects to a MongoDB database to store Mars weather data.
- Fetches real-time weather data from the NASA InSight API.
- Parses JSON data and inserts it into the MongoDB collection.
- Retrieves stored weather data from MongoDB.
- Uses environment variables to manage sensitive information securely.

## Prerequisites

To run this project, ensure you have the following installed:

- Java 11 or higher
- Maven
- MongoDB
- An active internet connection to access the NASA API

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/capybara-brain346/Mars-Data-ETL-Pipeline.git
cd mars-weather-data
```

### 2. Set Up Environment Variables

Create a `.env` file in the root directory of your project with the following variables:

```
DB_USERNAME=your_mongodb_username
DB_PASSWORD=your_mongodb_password
DB_URI=your_mongodb_uri
NASA_API_KEY=your_nasa_api_key
```

Replace the placeholder values with your actual MongoDB credentials and NASA API key.

### 3. Build the Project

Use Maven to build the project:

```bash
mvn clean install
```

### 4. Run the Application

You can run the application using the following command:

```bash
java -cp target/mars-weather-data-1.0-SNAPSHOT.jar org.mars.Load
```

### 5. Fetch Data from NASA API

The `Main` class can be modified to call the `sendDataToMongoDB` method, which fetches data from the NASA API and inserts it into MongoDB. Uncomment the relevant line in the `main` method:

```java
// sendDataToMongoDB(getAPI, mongoDBClient);
```

### 6. Retrieve Data from MongoDB

The application retrieves stored weather data from MongoDB using the `retrieveDataFromMongoDB` method in the `Main` class. This method prints the retrieved documents to the console.

## Code Structure

- **MongoDBClient**: Manages connections to the MongoDB database and handles data insertion and retrieval.
- **Main**: Entry point for the application; orchestrates data fetching and processing.
- **GetAPI**: Handles HTTP requests to the NASA API to retrieve weather data.
- **DataRepository**: Responsible for inserting weather data into a relational database (not fully defined in the provided code).
- **DataModel**: Represents the data structure for storing Mars weather data (not fully defined in the provided code).
- **DateUtil**: Utility class for date formatting (not fully defined in the provided code).
  
## Dependencies

The following libraries are used in this project:

- [MongoDB Java Driver](https://mongodb.github.io/mongo-java-driver/)
- [OkHttp](https://square.github.io/okhttp/)
- [Jackson](https://github.com/FasterXML/jackson)
- [dotenv-java](https://github.com/cdimascio/dotenv-java)

### Maven Dependencies

Add the following dependencies to your `pom.xml`:

```xml
<dependencies>
    <!-- MongoDB Java Driver -->
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>4.6.0</version>
    </dependency>
    
    <!-- OkHttp -->
    <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>okhttp</artifactId>
        <version>4.9.2</version>
    </dependency>
    
    <!-- Jackson Databind -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.13.1</version>
    </dependency>
    
    <!-- dotenv-java -->
    <dependency>
        <groupId>io.github.cdimascio</groupId>
        <artifactId>dotenv-java</artifactId>
        <version>3.1.0</version>
    </dependency>
</dependencies>
```

# Mars Weather Analytics Dashboard  

An interactive dashboard for visualizing weather conditions on Mars. The project uses data from a MySQL database, processes JSON-formatted weather attributes, and presents insights using a Dash web application.  

## Features  
- **Visualize Trends:** Analyze pressure, temperature, and wind speed variations over time.  
- **Interactive Visualization:** Explore relationships like temperature vs. pressure and wind direction distribution.  
- **Custom Styling:** Dark-themed dashboard with vibrant color schemes for better readability.  

## Project Components  

### 1. Airflow DAG for Running a Java Task  
This DAG runs a Java JAR file as part of an automated workflow.  

```python
from airflow.operators.bash import BashOperator
from datetime import datetime
from airflow import DAG

default_args = {
    'owner': 'airflow',
    'start_date': datetime(2024, 10, 31),
    'retries': 1,
}

dag = DAG(
    'run_java_jar',
    default_args=default_args,
    schedule_interval='@daily',
)

run_java_jar = BashOperator(
    task_id='run_java_jar_task',
    bash_command='java -jar /app/Extract-1.0-SNAPSHOT.jar',
    dag=dag,
)
```

### 2. Docker Compose Configuration  
Sets up an Airflow environment with PostgreSQL as the backend and provisions the services required for execution.  

```yaml
version: "3"
x-airflow-common: &airflow-common
  image: apache/airflow:2.0.0
  environment:
    - AIRFLOW__CORE__EXECUTOR=LocalExecutor
    - AIRFLOW__CORE__SQL_ALCHEMY_CONN=postgresql+psycopg2://postgres:postgres@postgres:5432/airflow
    - AIRFLOW__CORE__FERNET_KEY=FB0o_zt4e3Ziq3LdUUO7F2Z95cvFFx16hU8jTeR1ASM=
    - AIRFLOW__CORE__LOAD_EXAMPLES=False
    - AIRFLOW__CORE__LOGGING_LEVEL=INFO
  volumes:
    - ./dags:/opt/airflow/dags
    - ./airflow-data/logs:/opt/airflow/logs
    - ./airflow-data/plugins:/opt/airflow/plugins
  depends_on:
    - postgres

services:
  postgres:
    image: postgres:12
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
      - POSTGRES_DB=airflow
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

  airflow-init:
    <<: *airflow-common
    container_name: airflow_init
    entrypoint: /bin/bash
    command:
      - -c
      - |
        airflow db init &&
        airflow users create \
          --role Admin \
          --username airflow \
          --password airflow \
          --email airflow@airflow.com \
          --firstname airflow \
          --lastname airflow
    restart: "no"
    depends_on:
      - postgres

  airflow-webserver:
    <<: *airflow-common
    command: airflow webserver
    ports:
      - "8081:8080"
    container_name: airflow_webserver
    restart: always
    depends_on:
      - postgres
      - airflow-init

  airflow-scheduler:
    <<: *airflow-common
    command: airflow scheduler
    container_name: airflow_scheduler
    restart: always
    depends_on:
      - postgres
      - airflow-init

volumes:
  postgres-data:
```

### 3. Dash Application for Visualization  
The Dash app processes data from the MySQL database, extracts JSON attributes, and displays the processed data in various interactive graphs.  


## Visualization Examples  
- **Pressure Variation:** Average, min, and max pressure over time.  
- **Temperature Profiles:** Trends in Mars temperature with highlighted extremes.  
- **Wind Speed & Direction:** Analyze wind speed changes and directional distributions.  


## Contribution

Contributions are welcome! If you have suggestions for improvements or want to report a bug, please create an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to NASA for providing the InSight weather data API.
- Special thanks to the open-source community for the libraries used in this project.

