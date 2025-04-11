# User Management Application

A full-stack **User Management System** built with **React** on the frontend and **Spring Boot** on the backend. This application allows you to manage users through CRUD operations and demonstrates RESTful API integration, routing, and MySQL database interaction.

## 🛠️ Tech Stack

### Frontend
- React 18
- Axios
- Bootstrap 5
- React Router DOM
- React Testing Library & Jest

### Backend
- Spring Boot 3.3.3
- Spring Data JPA
- MySQL
- Jackson Databind
- Lombok
- REST Assured (Testing)

---

## 📁 Project Structure

user-management/
│
├── backend/
│   └── userManage/                 # Spring Boot project
│       ├── src/
│       │   └── main/
│       │       ├── java/          # Java source code
│       │       └── resources/     # application.properties
│
└── frontend/
    └── usemanage-front/           # React application
        ├── public/
        └── src/


---

## 🚀 Getting Started

### Prerequisites

- Node.js ≥ 14
- Java 21
- Maven
- MySQL Server

### 🧩 Backend Setup

1. **Clone the repository** and navigate to the backend folder:

   ```bash
   cd backend/userManage

2. **Configure your database connection** in src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/userdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

3. **Run the backend**:
./mvnw spring-boot:run

🌐 Frontend Setup

1. Navigate to the frontend directory:
cd frontend/usemanage-front

2. Install dependencies:
npm install

3. Run the development server:
npm start

**The app will start at**: http://localhost:3000

## 🔌 API Endpoints (Sample)

| Method | Endpoint           | Description       |
|--------|--------------------|-------------------|
| GET    | `/api/users`       | Get all users     |
| GET    | `/api/users/{id}`  | Get user by ID    |
| POST   | `/api/users`       | Create new user   |
| PUT    | `/api/users/{id}`  | Update user       |
| DELETE | `/api/users/{id}`  | Delete user       |

✅ Testing
Backend Tests
./mvnw test

Frontend Tests
npm test

## 📌 Dependencies Overview

### Frontend
- **axios** – For HTTP requests.
- **bootstrap** – For styling and UI.
- **react-router-dom** – For SPA navigation.
- **@testing-library** – For unit testing React components.

### Backend
- **spring-boot-starter-web** – To build REST APIs.
- **spring-boot-starter-data-jpa** – ORM with Hibernate.
- **mysql-connector-java** – MySQL database driver.
- **lombok** – Reduces boilerplate code.
- **rest-assured** – API testing library.

---

## 📄 License

This project is licensed under the **MIT License**.

---

## 🙌 Acknowledgments

Special thanks to the **open-source community** and documentation resources that made this project possible.

## ✍️ Author

**Mzwili** – [LinkedIn](https://www.linkedin.com/in/mzwi-linked/) • [GitHub](https://github.com/mzwili)

