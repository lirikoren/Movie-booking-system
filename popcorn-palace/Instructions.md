# Movie Theatre Booking System

A web-based application that allows users to view showtimes, book tickets, and manage movie theater showtimes. The system includes features such as booking tickets, ensuring no double booking of seats, and managing showtimes for different theaters.

## Features

- **User Booking**: Users can book tickets for available showtimes.
- **Showtime Management**: Admin can add, update, or delete showtimes.
- **Seat Reservation**: Ensures that no seat is double-booked for the same showtime.
- **Movie Management**: Movies are associated with showtimes, and each movie has relevant details (e.g., title, genre, runtime).

## Technologies Used

- **Backend**: Java with Spring Boot
- **Database**: H2 (for development) / MySQL (for production)
- **Testing**: JUnit 5, Mockito
- **Build Tool**: Maven

## Getting Started

### Prerequisites

To run the project locally, you will need to have the following installed:

- **Java 17** (or later)
- **Maven** (for building the project)
- **Git** (for version control)

### Cloning the Project

1. Open your terminal.
2. Clone the repository using Git:

   ```bash
   git clone https://github.com/lirikoren/movie-theatre-booking-system.git

3. Navigate to the project directory:

   ```bash
   cd movie-theatre-booking-system

### Building and Running the Application

1. **Build the project** with Maven:

   ```bash
   mvn clean install

2. **Run the application** :
   if you're using intelliJ IDE:
   Click the play (run) button in the IDE to run the project.
   Or, from the terminal, you can run the application using Maven:
    ```bash
   mvn spring-boot:run

3. **Running tests** :
   To run all tests for the project, use the following command:
   ```bash
   mvn test



