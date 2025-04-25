<div align="center">
  <h1>🚀 Spring Boot 3 with Firebase Auth & Storage</h1>
</div>

---

## 📑 Table of Contents
- [Java 17 Installation](#java-17-installation)
- [Git Installation](#git-installation)
- [Clone the Project Using GitHub Token](#clone-the-project-using-github-token)
- [Docker Installation](#docker-installation)
- [PostgreSQL Installation](#postgresql-installation)
- [Environment Variable Configuration](#environment-variable-configuration)
- [Run the Application with Docker Compose](#run-the-application-with-docker-compose)

---

## 🔧 Java 17 Installation
To install Java 17 on Ubuntu 24.04, follow these steps:

### 1️⃣ **Update the Package Index**
```bash
sudo apt update
```

### 2️⃣ **Install Java 17**
```bash
sudo apt install openjdk-17-jdk -y
```

### 3️⃣ **Verify the Installation**
```bash
java -version
```
Expected output:
```bash
openjdk version "17.x.x" ...
```

---

## 🛠 Git Installation
To install Git on Ubuntu 24.04:

### 1️⃣ **Install Git**
```bash
sudo apt install git -y
```

### 2️⃣ **Verify the Installation**
```bash
git --version
```
Expected output:
```bash
git version 2.x.x
```

---

## 🔑 Clone the Project Using GitHub Token

### 1️⃣ **Generate a GitHub Token**
1. Navigate to [GitHub Tokens](https://github.com/settings/tokens)
2. Create a token and copy it.

### 2️⃣ **Clone the Repository**
```bash
git clone https://github.com/<organizationname>/<reponame>.git
```

### 3️⃣ **Configure Git**
```bash
git config --global user.email "your-email@example.com"
git config --global user.name "YourUsername"
```

### 4️⃣ **Set Remote with GitHub Token**
```bash
git remote set-url origin https://<username>:<token>@github.com/<organizationname>/<reponame>.git
```

---

## 🐳 Docker Installation

### 1️⃣ **Install Docker & Docker Compose**
```bash
sudo apt update && sudo apt install -y docker.io docker-compose
```

### 2️⃣ **Verify Installation**
```bash
docker --version
docker-compose --version
```

### 3️⃣ **Enable and Start Docker**
```bash
sudo systemctl enable docker
sudo systemctl start docker
```

### 4️⃣ **Manually Start Docker Daemon in WSL**
```bash
sudo dockerd &
docker ps
```

---

## 🛢 PostgreSQL Installation

### 1️⃣ **Pull the PostgreSQL Docker Image**
```bash
docker pull postgres
```

### 2️⃣ **Run a PostgreSQL Container**
```bash
sudo docker run --name postgres_container -e POSTGRES_USER=root -e POSTGRES_PASSWORD=password -e POSTGRES_DB=test_db -p 5432:5432 -d postgres
```

### 3️⃣ **Verify Running Container**
```bash
sudo docker ps
```

### 4️⃣ **Access PostgreSQL**
#### Using `psql` inside the container:
```bash
docker exec -it postgres_container psql -U root -d test_db
```

---

## 🌍 Environment Variable Configuration

### 1️⃣ **Update `.env` File**
- Add a `.env` file in the project directory and update it using the database credentials from above.

### 2️⃣ **Configure Firebase Service Account**
1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Select your project or create a new one.
3. Navigate to **Project Settings** > **Service Accounts**.
4. Click **Generate New Private Key** and download the JSON file.
5. Rename the file to `firebase-service-account.json` and place it in your project directory.

---

## 🏃‍♂️ Run the Application with Docker Compose

### 1️⃣ **Verify Prerequisites**
```bash
sudo docker compose --version
```
Ensure PostgreSQL is not running separately on port `5432` to avoid conflicts.

### 2️⃣ **Build and Start Services**
```bash
sudo docker compose build
sudo docker compose up -d
```

### 3️⃣ **Check Running Services**
```bash
sudo docker compose ps
```

### 4️⃣ **Test Database Connectivity**
```bash
docker exec -it postgres_container psql -U root -d test_db
```
Run a sample query:
```sql
\dt
```

### 5️⃣ **Access the Backend API**
Open a browser or use Postman to test:
```bash
http://localhost:8000/swagger-ui/index.html#/
```

### 6️⃣ **Stop Services**
```bash
sudo docker compose down
```

