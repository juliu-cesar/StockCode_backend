# Stock Code project

## Installation

1. Install Java 17

```bash
sudo apt install openjdk-17-jdk openjdk-17-jre
```

2. Install Maven on Ubuntu

```bash
apt-cache search maven

sudo apt-get install maven
```

We can run the `mvn -version` to verify our installation.

3. Install Docker

```bash

```

4. Clone repository

```bash
git clone https://github.com/juliu-cesar/StockCode_backend.git
```

5. Check if `mvnw` and `mvnw.cmd` have permission. If not, run the command:

```bash
chmod +rwx mvnw

chmod +rwx mvnw.cmd
```

## Executing program

1. Run docker compose

```bash
docker compose up -d
```

2. Use your IDE to start the project.

3. Fill tables with **mock information**. Make a POST request to the following route:

```http
http://localhost:8080/fill-all-tables
```
