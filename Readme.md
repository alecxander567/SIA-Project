# SIA-Project

A comprehensive admin dashboard and management system designed for administrators to efficiently manage products, orders, and employee statuses. The application features a modern, responsive interface with seamless front-end and back-end integration.

## 🚀 Features

- **Product Management**
  - View all products in an organized dashboard
  - Add, edit, and delete products
  - Search products by name
  - Filter products by category
  - Image upload support for products

- **Order Management**
  - Track order statuses in real-time
  - Update order information
  - View order history

- **Employee Management**
  - Monitor employee statuses
  - Role-based access control (Admin, Delivery, Employee)
  - Secure authentication and authorization

- **Analytics Dashboard**
  - View top-selling items
  - Monitor business metrics
  - Data visualization

## 🛠️ Tech Stack

### Frontend
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
![React Router](https://img.shields.io/badge/React_Router-CA4245?style=for-the-badge&logo=react-router&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)

### Backend
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

## 🔐 API Endpoints

### Authentication
- `POST /api/employees/signup` - Register a new employee
- `POST /api/employees/login` - Authenticate user
- `POST /api/employees/logout` - Logout user

### Products
- `GET /api/items` - Get all products
- `GET /api/items/{id}` - Get product by ID
- `POST /api/items` - Add new product
- `PUT /api/items/{id}` - Update product
- `DELETE /api/items/{id}` - Delete product
- `GET /api/items/search?name={name}` - Search products by name
- `GET /api/items/category/{category}` - Filter by category

### Orders
- `GET /api/orders` - Get all orders
- `PUT /api/orders/{id}` - Update order status

### Employee Status
- `GET /api/statuses` - Get all employee statuses
- `POST /api/statuses/add` - Add new status
- `PUT /api/statuses/edit/{id}` - Update status
- `DELETE /api/statuses/delete/{id}` - Delete status

## 🏗️ Backend Architecture

### Key Components

**User Management:**
- `registerEmployee(SignupRequestDTO request)` - Registers new employees with unique email validation and BCrypt password encryption
- `loginEmployee(LoginDTO request)` - Authenticates users by verifying credentials
- Custom JPA queries: `existsByEmail`, `findByEmail`

**Product Management:**
- `getAllItems()` - Retrieves all products
- `getItemById(Integer id)` - Gets product by ID
- `addItem(Item item)` - Adds new products
- `updateItem(Integer id, Item updatedItem, MultipartFile imageFile)` - Updates products with image support
- `deleteItem(Integer id)` - Deletes products
- `getItemsByCategory(String category)` - Filters by category
- `searchItemsByName(String name)` - Search functionality

**Security Features:**
- BCrypt password encryption
- Role-based access control
- Session management with credentials

**Data Persistence:**
- Spring Data JPA for ORM
- Repository pattern implementation
- Entity classes mapped to MySQL tables

## 🔒 Security

- Passwords are encrypted using BCrypt before storage
- Role-based access control ensures proper authorization
- Secure session management with HTTP-only cookies
- CORS configuration for secure cross-origin requests

