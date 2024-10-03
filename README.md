
# FlowCRM Software Project

## Instructions for Running Locally

### Prerequisites
Before starting, ensure you have the following installed:
- **Docker**
- **Docker Compose**
- **Java 17**
- **Node.js and npm** (use Node version **20.1.15**)

### Database Deployment and Initial Setup

1. Navigate to the `deployment/common` directory:
   ```bash
   cd deployment/common
   ```
2. Start the database using Docker Compose:
   ```bash
   docker-compose up -d
   ```
3. Run the **RestControllerApplication** (requires Java 17) in your IDE to handle database migrations.
4. Connect to the database using the following credentials:
    - **JDBC URL**: `jdbc:mysql://localhost:3306/flow_crm?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
    - **Username**: `admin`
    - **Password**: `password`
5. Run the **NotificationControllerApplication** in your IDE (requires Java 17) for WebSocket communication and notifications.
6. Execute the `insert.sql` file on the database to insert initial data.

### Frontend Setup

1. Navigate to the `ui` directory:
   ```bash
   cd ui
   ```
2. Install the project dependencies:
   ```bash
   npm install
   ```
3. Start the frontend application:
   ```bash
   npm run start
   ```

### Accessing the Application

1. Open your browser and go to:
   ```
   http://localhost:3000
   ```
2. Use the following credentials to log in, with different roles available:
    - **Username**: `admin`, `systemAdmin`, `customer_ali`
    - **Password**: `1234567a`
