# DocumentSearch

Prototype public-service-portal-style app (loosely modeled on Cổng Dịch vụ công Quốc gia). Users can search their documents by keyword or by ID scanned from a QR code. Guests see a redacted preview; logged-in users see the full document (only for documents they own).

- **Backend:** Java 21, Spring Boot 3, Spring Data JPA, H2 (in-memory), Spring Security + JWT, ZXing for QR, Thymeleaf for the mock VNeID picker
- **Frontend:** Angular 18, standalone components, signals, Angular Router
- **Persistence:** H2 in-memory, seeded via `schema.sql` + `data.sql` on every boot (20 users, 20 documents, 40 attachments)

---

## Prerequisites

| Tool | Version |
|---|---|
| Java | 21 |
| Maven | 3.9+ |
| Node.js | 20.x |
| npm | 10.x |

---

## Run

Open two terminals.

### 1. Backend (port 8080)

```bash
cd backend
mvn spring-boot:run
```

- Search API: `GET http://localhost:8080/api/documents/search?q=<text>`
- Document detail: `GET http://localhost:8080/api/documents/{id}`
- QR PNG: `GET http://localhost:8080/api/documents/{id}/qr.png`
- Mock VNeID picker: `http://localhost:8080/api/auth/mock-vneid/authorize?redirect_uri=...&state=...`
- H2 console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:docsearch`, user `sa`, empty password)

### 2. Frontend (port 4200)

```bash
cd frontend
npm install          # first time only
npm start
```

Open `http://localhost:4200`.

---

## Demo flows

### Guest keyword search
1. Open `http://localhost:4200`.
2. Type e.g. `Nguyễn` or `Hoàn Kiếm` or `KS-2023` in the search bar → Enter.
3. Results list appears; each card shows masked owner name (`Nguyễn V*** A`).

### QR deep-link (same laptop)
1. Open `http://localhost:4200/search?id=22222222-2222-2222-2222-000000000001`
2. Bar is prefilled, detail loads inline in **preview** mode (masked name, no body, no attachments).

### QR deep-link (from phone)
1. Start the app as above.
2. Find your LAN IP (e.g. `192.168.1.42`). Restart the backend with:
   ```bash
   cd backend
   mvn spring-boot:run -Dspring-boot.run.arguments='--app.public-base-url=http://192.168.1.42:4200 --app.export-qr=true'
   ```
3. QR PNGs land in `backend/seed-output/qr/`. Display or print one on a second screen.
4. Scan with an iPhone → Safari opens the deep link → search bar auto-fills, preview renders.

### Login + full view
1. Click **Đăng nhập** in the header.
2. Mock VNeID picker appears with 20 seeded citizens. Pick one, e.g. **Nguyễn Văn An**.
3. Redirected back to `/auth/callback`, JWT exchanged, home reloads with "Nguyễn Văn An" in the header.
4. Navigate to `/documents/22222222-2222-2222-2222-000000000001` (or scan An's QR) → **full** view with Vietnamese body, attachment list, full name, DOB, CCCD, address.
5. Open `/documents/22222222-2222-2222-2222-000000000002` (Trần Thị Bình's doc) while still logged in as An → stays in **preview** mode (you don't own it).

---

## Seed data overview

20 documents, 4 per type:

| Type | Vietnamese | Valid | Expired | Revoked |
|---|---|---|---|---|
| `BIRTH_CERT` | Giấy khai sinh | 4 | 0 | 0 |
| `MARRIAGE_CERT` | Giấy đăng ký kết hôn | 4 | 0 | 0 |
| `BUSINESS_REG` | Giấy phép kinh doanh | 2 | 1 | 1 |
| `DRIVER_LICENSE` | Giấy phép lái xe | 2 | 1 | 1 |
| `LAND_USE_RIGHT` | Giấy chứng nhận quyền sử dụng đất | 3 | 1 | 0 |

Anchor demo users:
- **Nguyễn Văn An** — BIRTH_CERT, VALID (primary QR demo)
- **Trần Thị Bình** — MARRIAGE_CERT, VALID
- **Đặng Văn Giang** — BUSINESS_REG, EXPIRED
- **Bùi Thị Hương** — DRIVER_LICENSE, REVOKED

UUIDs are deterministic (literal in `data.sql`) so printed QR codes stay valid across restarts.

---

## Repository layout

```
DocumentSearch/
├── PLAN.md                               # Original design plan
├── README.md                             # This file
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/viettel/docsearch/   # Application code (domain, repo, service, web, config, demo)
│       └── resources/
│           ├── application.yml
│           ├── schema.sql                # DDL, runs on every boot
│           ├── data.sql                  # Seed, runs on every boot
│           └── templates/
│               └── mock-vneid-picker.html
└── frontend/
    ├── package.json
    ├── angular.json
    └── src/app/
        ├── core/                         # auth, interceptor, services, models
        ├── shared/                       # header, nav, search bar, feature buttons
        └── pages/                        # home, search, document-detail, auth-callback, placeholder
```

Generated at runtime:
- `backend/seed-output/qr/*.png` — one 512×512 QR per document (only when `app.export-qr=true`)
- `backend/target/` — Maven build output (gitignore)
- `frontend/node_modules/`, `frontend/dist/`, `frontend/.angular/` — npm/build output (gitignore)

---

## Configuration

`backend/src/main/resources/application.yml`:

| Key | Default | Purpose |
|---|---|---|
| `app.public-base-url` | `http://localhost:4200` | Used to build the deep link inside each QR code. Override for phone-scan demos. |
| `app.jwt.secret` | prototype string | HMAC key for JWT. Must be ≥ 32 bytes. |
| `app.jwt.ttl-minutes` | 120 | Token lifetime. |
| `app.export-qr` | `false` | When `true`, writes all QR PNGs to `seed-output/qr/` on app ready. |

All keys can be overridden via CLI args: `--app.public-base-url=http://x.x.x.x:4200`.

---

## Current limitations

- In-memory H2: all data is lost on backend restart. `data.sql` reseeds deterministically.
- JWT is held in Angular memory only (not localStorage), so a browser reload logs you out.
- "Tìm kiếm nâng cao" (advanced search) link is a placeholder (`alert`).
- Payments / Feedback / Help pages are static placeholders.
- No admin UI to create documents; seed data is hand-curated in `data.sql`.
