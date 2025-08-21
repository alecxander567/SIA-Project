# SIA-Project

SIA-Project is a comprehensive admin dashboard and management system designed for administrators to manage products, orders, and employee statuses efficiently. The application delivers a modern and responsive interface, with seamless integration between front-end and back-end components.

## Technologies Used

**Front End:**
- JavaScript
- React
- Tailwind CSS

**Back End:**
- Java Spring Boot
- MySQL

[![My Skills](https://skillicons.dev/icons?i=js,react,tailwind,java,spring,mysql)](https://skillicons.dev)

## Backend Implementation Methods

The backend leverages Spring Boot, JPA, and RESTful APIs. Key methods and approaches include:

- **User Management:**
  - `registerEmployee(SignupRequestDTO request)`: Registers a new employee after checking for unique email and encodes the password with BCrypt.
  - `loginEmployee(LoginDTO request)`: Authenticates users by verifying email and encrypted passwords.
  - User data is managed using JPA repositories (`UserRepository`) with custom queries like `existsByEmail` and `findByEmail`.

- **Product Management:**
  - `getAllItems()`: Retrieves all products.
  - `getItemById(Integer id)`: Gets a product by its ID.
  - `addItem(Item item)`: Adds new products.
  - `updateItem(Integer id, Item updatedItem, MultipartFile imageFile)`: Updates existing products, including handling image uploads.
  - `deleteItem(Integer id)`: Deletes products by ID.
  - `getItemsByCategory(String category)`: Filters products by category.
  - `searchItemsByName(String name)`: Searches for products by name.

- **API Structure:**
  - REST endpoints for items: `GET /api/items`, `POST /api/items`, `PUT /api/items/{id}`, `DELETE /api/items/{id}`, `GET /api/items/search`.
  - REST endpoints for users: `POST /api/employees/signup`, `POST /api/employees/login`, `POST /api/employees/logout`.

- **Security:**
  - Passwords are encrypted with BCrypt.
  - Role assignment is based on user position (admin, delivery, employee).

- **Data Persistence:**
  - Uses Spring Data JPA for ORM and repository patterns.
  - Entity classes are mapped to MySQL database tables.

## Features

- View all products
- Add, edit, and delete products
- Track and update order statuses
- Monitor employee statuses
- View analytics for top-selling items

## Getting Started

### Prerequisites

- Node.js and npm (for the front end)
- Java (for the backend)
- MySQL (for the database)


