# PromptShop — AI Prompt Store

Single-store AI prompt marketplace with a polished storefront, instant demo checkout, customer library, and admin management tools.

## Stack

- **Backend:** Spring Boot (Java 21), Spring Security (JWT), Spring Data JPA, PostgreSQL
- **Frontend:** Vite + Vanilla JS + modern CSS design system
- **Infra:** Docker Compose (frontend + backend + postgres)

## Implemented functionality

- Customer auth (register/login/logout) with JWT
- Public prompt catalog with pricing and categories
- Cart + instant demo checkout (marks paid immediately)
- Customer order history and purchased prompt library
- Copy prompt to clipboard and download purchased prompt as `.txt`
- Admin dashboard:
  - Prompt CRUD (create/edit/delete/publish)
  - Order listing and status updates
  - Summary metrics (users/prompts/orders/revenue)
- Seeded demo users and prompt catalog

## Demo credentials

- **Admin:** `admin@promptshop.dev` / `Admin123!`
- **Customer:** `customer@promptshop.dev` / `Customer123!`

## Local run (without Docker)

### 1. Start Postgres

Use Docker quickly:

```bash
docker run --name promptshop-postgres \
  -e POSTGRES_DB=promptshop \
  -e POSTGRES_USER=promptshop \
  -e POSTGRES_PASSWORD=promptshop \
  -p 5432:5432 -d postgres:17-alpine
```

### 2. Start backend

```bash
cd backend
./mvnw spring-boot:run
```

Backend runs on `http://localhost:8080`.

### 3. Start frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on `http://localhost:5173`.

## Docker run (recommended for presentation)

```bash
docker compose up --build
```

Services:

- Frontend: `http://localhost:5173`
- Backend API: `http://localhost:8080/api`
- PostgreSQL: `localhost:5432`

## Main API routes

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/me`
- `GET /api/prompts`
- `POST /api/orders/checkout`
- `GET /api/orders/my`
- `GET /api/library`
- `GET /api/admin/dashboard`
- `GET /api/admin/prompts`
- `POST /api/admin/prompts`
- `PUT /api/admin/prompts/{id}`
- `DELETE /api/admin/prompts/{id}`
- `GET /api/admin/orders`
- `PATCH /api/admin/orders/{id}/status?status=PAID`
