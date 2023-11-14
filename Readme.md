# Stock Code

![banner-stock-code](https://github.com/juliu-cesar/StockCode_backend/assets/121033909/6759750b-66ff-4d14-9610-163dbdf0a6ec)

## About

This project is an API built in **Java** with **Spring Boot** and **MySQL**, developed to facilitate efficient management of a store's inventory. With comprehensive features, the application provides functionality for creating brands, categories, and products, as well as enabling the control of purchase and return transactions.

### Key Features

1. Management of Brands, Categories, and Products

    - Brands: Create and manage brands associated with products.
    - Categories: Organize products into categories to facilitate search and navigation.
    - Products: Create products by associating them with brands and categories.

2. Inventory Control
    - Stock Addition: Record the entry of products into inventory, specifying the quantity and other relevant details.
    - Purchase Transactions: Carry out purchase transactions that automatically update the inventory by removing the purchased items.

3. Transactions and Returns

    - Transaction Recording: In the *TransactionController* class, it is possible to create transactions by providing the date, customer name, and the list of purchased products. This action is integrated with the inventory system, ensuring data consistency.
    - Returns: If necessary, customers can make returns, indicating the product, reason, quantity, and whether the item should be returned to inventory. This functionality simplifies return management while maintaining inventory integrity.

### Usage

This project is intended for small businesses that need a simple and efficient solution for product inventory management. The API provides a solid foundation for controlling inventory, optimizing processes of purchase, sale, and return.

## Technologies

- ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

- ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

- ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

- ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Make the requests

- Create product

https://github.com/juliu-cesar/StockCode_backend/assets/121033909/0fefbda0-b76d-4836-ad98-38fa212ac75e

- Buy product

https://github.com/juliu-cesar/StockCode_backend/assets/121033909/8f2cbd6c-0ee7-4f8c-8766-ef96cd8d97cb

- Refound product

https://github.com/juliu-cesar/StockCode_backend/assets/121033909/9f90fdbb-c4de-44df-9e05-16aadd9a9aa3

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

3. Install Docker. Follow this installation guide [Install Docker Engine on Ubuntu](https://docs.docker.com/engine/install/ubuntu/#installation-methods)

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

3. Fill tables with **mock information**. Make a *POST* request to the following route:

```http
http://localhost:8080/fill-all-tables
```

4. Use [Insomnia](https://insomnia.rest/download), [Postman](https://www.postman.com/downloads/), or the [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) to make requests. If you choose the **REST Client** extension, the requests are already in the [requests](/requests/) folder.
