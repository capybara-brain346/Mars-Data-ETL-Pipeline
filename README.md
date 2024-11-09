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

## Contribution

Contributions are welcome! If you have suggestions for improvements or want to report a bug, please create an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to NASA for providing the InSight weather data API.
- Special thanks to the open-source community for the libraries used in this project.

```

### Notes
- Ensure any utility classes or models not defined in the provided code (like `DataRepository`, `DataModel`, `DateUtil`) are mentioned clearly, or provide links to their definitions if they exist.
- Adjust the Maven dependency versions based on your project requirements or latest available versions.
