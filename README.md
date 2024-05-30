# Project Management in Java

## Description

This project is a Java-based project management application designed for an exam.
It allows for quick management of projects by associating specific tasks with them.

## Features

### Project Management

- **List projects**: Display a list of all projects.
- **Read a detailed description of a project**: View the detailed information of a selected project.
- **Describe the project**: Provide a detailed description.
- **Create a project**: Create a new project.
- **Update a project**: Update the details of a project.
- **Close a project**: Define an end date for the project.
- **Delete a project**: Remove a selected project.

### Task Management

- **List tasks**: Display a list of all tasks related to a project.
- **Read a detailed description of a task**: View the detailed information of a selected task.
- **Describe the task**: Provide a detailed description.
- **Create a task**: Create a new task.
- **Update a task**: Update the details of a task.
- **Close a task**: Define an end date for the task.
- **Delete a task**: Remove a selected task.

### Chat

- **Communicate with a person using the same software**: On the same server, just provide the port number and send
  messages.

## Prerequisites

- **Java SDK**: Version 21
- **PostgreSQL**: Latest version
- **Maven**: Version 3.9.6
- **IntelliJ IDEA**: Latest version
- **Configuration File**: Create a `config.ini` file for configuration settings from
  the `config.example.ini` [file](./src/main/resources/config.example.ini) present in the `resources` folder.
- **Database Script**: After creating the `config.ini` file and setting all requested parameters, create a database with
  the name you provide and use the [script](./src/main/resources/db/db_init.sql) present in the `resources` folder to
  deploy the database structure.

## Installation

1. **Clone the repository**:
    ```sh
   git clone https://github.com/ThomasDeOliv/JExam
    ```

2. **Configure PostgreSQL**:

   Create a PostgreSQL database. Configure the connection settings in the application properties file.


3. **Build the project with Maven**:
    ```sh
    mvn clean install
    ```

4. **Run the project**:

- Open the project in IntelliJ IDEA.
- Run the main class to start the application.


5. **Optionally, if you need to reinstall the maven dependencies**:
    ```sh
    mvn clean install -U
    ```

## Usage

1. Create a project:

   Access the project management interface.
   Enter the required information (name, description, dates).


2. Add tasks:

   Select an existing project.
   Access the task section and add new tasks by providing the required details.

## Licence

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.