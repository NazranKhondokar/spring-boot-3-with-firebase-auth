<div align="center">
  <h1>Spring Boot 3 with Firebase Auth & Storage</h1>
</div>

# Table of Contents

- [Install Java 17](#java-17-installation)
- [Install Git](#git-installation)
- [Configure Github](#clone-the-project-using-github-token)
- [Docker Installation](#docker-installation)
- [PostgreSQL Installation](#postgresql-installation)
- [Environment Variable Configure](#environment-variable-configure)
- [Run with `docker compose`](#run-application-using-docker-compose)


# Java 17 Installation
To install Java 17 on Ubuntu 24.04, you can follow these steps:

### 1. **Update the Package Index**
Open a terminal and run:
```bash
sudo apt update
```

### 2. **Install Java 17 from Ubuntu's Default Repository**
Ubuntu 24.04 includes OpenJDK in its default repositories. To install it, run:
```bash
sudo apt install openjdk-17-jdk -y
```

### 3. **Verify the Installation**
Check the installed Java version:
```bash
java -version
```
You should see output indicating that Java 17 is installed, like this:
```
openjdk version "17.x.x" ...
```

# Git Installation
To install Git on Ubuntu 24.04, follow these steps:

### **1. Install Git**
Install Git using the following command:
```bash
sudo apt install git -y
```

### **2. Verify the Installation**
Check if Git is installed and its version:
```bash
git --version
```
You should see output similar to:
```
git version 2.x.x
```

# Clone the project using github token

### Step 1: Create Token
1. Go to [Github Token](https://github.com/settings/tokens)
2. Create a token and copy that.

### Step 2: Clone the project

### Step 3: Configure `git`
```bash
  git config --global user.email <email>
  git config --global user.name <username>
```

### Step 4: Set remote with previous token
```bash
git remote set-url origin https://<username>:<token>@github.com/bduswork/bitcoinapps_backend.git
```

# Docker Installation

## **Step 1: Install Docker & Docker Compose**
Ensure **Docker** and **Docker Compose** are installed. If not, install them using:

```sh
sudo apt update && sudo apt install -y docker.io docker-compose
```

Verify installation:
```sh
docker --version
docker-compose --version
```

# PostgreSQL installation

### **Step 1: Pull the PostgreSQL Docker Image**
Download the latest PostgreSQL image from Docker Hub:
```bash
docker pull postgres
```

### **Step 2: Run a PostgreSQL Container**
Run the container with a specified name, username, and password:
```bash
sudo docker run --name postgres_container -e POSTGRES_USER=root -e POSTGRES_PASSWORD=password -e POSTGRES_DB=test_db -p 5432:5432 -d postgres
```

#### Explanation:
- `--name postgres_container`: Sets the container name.
- `-e POSTGRES_USER=root`: Creates a user named `root`.
- `-e POSTGRES_PASSWORD=password`: Sets the password for the user.
- `-e POSTGRES_DB=test_db`: Creates a database named `test_db`.
- `-p 5432:5432`: Maps the container's PostgreSQL port `5432` to the host's port `5432`.
- `-d`: Runs the container in detached mode.

### **Step 3: Verify the Container is Running**
Check if the PostgreSQL container is running:
```bash
sudo docker ps
```

You should see your `postgres_container` container listed.

### **Step 4: Access PostgreSQL**
You can access PostgreSQL in two ways:

#### 1. **Using `psql` in the Container**
Enter the running container:
```bash
docker exec -it postgres_container psql -U root -d password
```

#### 2. **Using a Database Client on the Host**
Use a PostgreSQL client (e.g., `psql`, DBeaver, or pgAdmin) to connect. Use the following credentials:
- **Host**: `localhost`
- **Port**: `5432`
- **Username**: `root`
- **Password**: `password`
- **Database**: `test_db`

# Environment Variable Configure

### Step 1: Update `.env`
- Add a `.env` file to project directory and update it using above DB credentials following `.env.example`

### Step 2: Update `firebase-service-account.json`
1. **Go to the Firebase Console**:
   - Open your browser and navigate to the [Firebase Console](https://console.firebase.google.com/).
   - Sign in with your Google account if you haven’t already.

2. **Select Your Project**:
   - From the Firebase Console dashboard, select the project you’re working on. If you don’t have a project yet, click "Add project" to create one, following the setup wizard.

3. **Navigate to Project Settings**:
   - Once your project is selected, click the gear icon in the top-left corner next to "Project Overview."
   - Select **Project settings** from the dropdown menu.

4. **Go to the Service Accounts Tab**:
   - In the Project Settings page, scroll down and click on the **Service accounts** tab.

5. **Generate a New Private Key**:
   - In the "Firebase Admin SDK" section, you’ll see an option to generate a service account key.
   - Click the **Generate new private key** button.
   - A confirmation dialog will appear. Click **Generate key** to proceed.

6. **Download the JSON File**:
   - After clicking "Generate key," a file will automatically download to your computer. It will be named something like `<your-project-name>-firebase-adminsdk-<random-string>.json`.
   - This is your `firebase-service-account.json` file. You can rename it to `firebase-service-account.json` for clarity if desired, but the contents remain the same.


# Run application using docker compose

### **Step 1: Verify Prerequisites**
1. **Docker Compose**: Ensure Docker Compose are installed on your machine:
   ```bash
   sudo docker compose --version
   ```

2. **PostgreSQL Setup in Docker**: Since the `database` service in your `docker-compose.yml` is already configured, you don't need a separate PostgreSQL instance running on your host. Make sure it's not conflicting with the port `5432`.


### **Step 2: Build and Start the Docker Compose Services**
Navigate to the directory where your `docker-compose.yml` is located.

1. **Build the Services**:
   ```bash
   sudo docker compose build
   ```

2. **Start the Services**:
   ```bash
   sudo docker compose up -d
   ```
    - `-d` runs the containers in detached mode.

3. **Verify the Services are Running**:
   ```bash
   sudo docker compose ps
   ```
   This will list the running containers with their status.

### **Step 3: Verify the Backend and Database**
1. **Access PostgreSQL**:
   Connect to the database container to confirm it is running:
   ```bash
   docker exec -it postgres_container psql -U root -d test_db
   ```
   You can run a test query, such as:
   ```sql
   \dt
   ```
   (This lists tables in the database.)

### **Step 4: Test Backend Accessibility**
1. Open your browser or use a tool like `curl` or Postman to access the backend at:
   ```bash
   http://localhost:8000/swagger-ui/index.html#/
   ```
   Ensure the backend responds as expected.

2. **Environment Variables**:
   Confirm the backend application is correctly using the provided `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD` to connect to the `database` service.

### **Step 5: Stop the Services**
To stop and remove the containers when you're done:
```bash
sudo docker compose down
```
