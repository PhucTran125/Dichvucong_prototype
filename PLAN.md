# DocumentSearch — Prototype Plan

A Spring Boot + Angular prototype that mimics Vietnam's national public service portal (Cổng Dịch vụ công Quốc gia). Users search for their document by keyword or by ID (auto-filled from a scanned QR code). Guests see a redacted preview; logged-in users see the full document.

---

## 1. Functional scope

| # | Capability | Notes |
|---|---|---|
| F1 | Full-text search of documents (title, type, issuing authority, masked owner name) | Guest-accessible |
| F2 | Search by ID (for QR deep-link) | Guest-accessible |
| F3 | Document detail — **preview** view | Guest-accessible; public fields only |
| F4 | Document detail — **full** view | Requires login; logged-in user can view *their own* document in full |
| F5 | Mock VNeID login (OAuth-style redirect flow) | Issues JWT for API auth |
| F6 | Per-document QR code image (PNG) | Encodes deep link `https://<host>/search?id=<docId>` |
| F7 | QR deep-link landing behavior | `/search?id=<docId>` auto-fills bar AND auto-fires search |
| F8 | Seeded mock data (users + documents + QR codes) | Ships on every startup |

Out of scope for the prototype (static chrome only, no backend): *Thanh toán trực tuyến*, *Phản ánh kiến nghị*, *Hỗ trợ*, advanced search modal.

---

## 2. Access-level matrix

| Viewer | Doc they own | Any other doc |
|---|---|---|
| Guest (not logged in) | Preview (public fields) | Preview (public fields) |
| Logged-in user | **Full** | Preview (public fields) |

**Assumption:** "Full" is gated on (authenticated AND `doc.owner_id = currentUser.id`). Decision lives in one place: `DocumentService#canViewFull`.

---

## 3. Tech stack

**Backend**
- Java 21, Spring Boot 3.x
- Spring Web, Spring Security (resource server), Spring Validation
- **Spring Data JPA** (Hibernate under the hood)
- **H2 database, file mode** as relational backing store — validation in §4
- **Spring Boot `schema.sql` + `data.sql`** auto-executed on startup — intentionally minimal, no migration framework for a prototype
- `com.google.zxing:core` + `zxing:javase` for QR generation
- JWT via `io.jsonwebtoken:jjwt`
- Build: Maven

**Frontend**
- Angular 17+ (standalone components, signals)
- Angular Router, HttpClient + JWT interceptor
- Plain CSS + CSS variables to match the portal's warm ochre/maroon palette from the screenshot. No UI kit.
- Vietnamese strings hardcoded (matches screenshot).

---

## 4. Persistence & data-mocking — tool evaluation

### 4.1 H2 validation

| Check | Result |
|---|---|
| Official Spring Boot support | ✅ `spring-boot-starter-data-jpa` + `com.h2database:h2` |
| Java 21 compatibility | ✅ H2 2.2.x |
| Run modes | In-memory (`jdbc:h2:mem:...`), **file** (`jdbc:h2:file:./data/docsearch`), or TCP server |
| Browsable web console | ✅ `/h2-console` auto-mounted by Spring Boot — great for demos |
| DDL strategy | Hibernate `ddl-auto` OR manual SQL via Flyway (we pick Flyway) |
| SQL dialect | Standard-enough; native `UUID`, `TIMESTAMP`, `CLOB`, `BOOLEAN` |
| Postgres compatibility | ✅ `;MODE=PostgreSQL` — eases future migration |
| Full-text search for Vietnamese diacritics | ⚠️ `FT_*` built-ins are basic. **Mitigation:** pre-fold (`Normalizer.NFD` + diacritic strip) into a `search_text` column, then `LIKE '%folded-term%'`. Sufficient for a prototype. |
| Concurrency | Single writer — fine for prototype |
| Production-ready? | ❌ embedded only — not a concern here |

**Verdict: H2 file mode is a good fit.** Real SQL, persistent QR-stable IDs across restarts, web console for live inspection, and a clean migration path to PostgreSQL later (change the JDBC URL, add the Postgres driver, re-run Flyway).

### 4.2 Alternatives considered

| Option | vs H2 | Verdict |
|---|---|---|
| SQLite + `hibernate-community-dialects` | No web console; community dialect (less official) | Worse DX |
| PostgreSQL in Docker | Prod-like, native `tsvector` FTS | Heavier setup; revisit post-prototype |
| Embedded Postgres (`io.zonky.test:embedded-postgres`) | Real Postgres, embedded | Fat JAR, slow boot; mostly a test-only tool |
| `ConcurrentHashMap` (earlier plan) | Zero setup but no SQL / no introspection | Rejected — we want the relational model |

### 4.3 Mock-data tool — options

| Tool | Role | Fit |
|---|---|---|
| **Spring Boot `schema.sql` / `data.sql`** | Auto-run on startup, zero extra dep | ✅ Primary. Two hand-edited files, nothing else to configure. |
| Flyway | Versioned SQL migrations | Deferred. Overkill for a 20-row prototype — easy to layer on later if the schema starts evolving. |
| Liquibase | Versioned migrations in XML/YAML | Skip |
| Datafaker (`net.datafaker:datafaker`) | Generates fake data in Java | Skip. The 20 demo users are hand-written in `data.sql`. |
| Testcontainers | DB in Docker for tests | Not a runtime tool |

**Chosen stack for persistence + mock data: H2 (file mode) + `schema.sql` + `data.sql`,** both hand-written, both under `src/main/resources/`.

---

## 5. Relational schema & JPA entities

### 5.1 DDL — `schema.sql`

```sql
-- Users (identity mirrored from mock VNeID)
CREATE TABLE app_user (
  id              UUID          PRIMARY KEY,
  vneid_subject   VARCHAR(64)   NOT NULL UNIQUE,
  full_name       VARCHAR(128)  NOT NULL,
  dob             DATE          NOT NULL,
  citizen_id      VARCHAR(20)   NOT NULL UNIQUE,
  address         VARCHAR(256)  NOT NULL,
  created_at      TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Documents (exactly one per user → owner_id UNIQUE)
CREATE TABLE document (
  id                   UUID          PRIMARY KEY,
  owner_id             UUID          NOT NULL UNIQUE,
  type                 VARCHAR(32)   NOT NULL,                -- enum string
  title                VARCHAR(256)  NOT NULL,
  document_number      VARCHAR(64)   NOT NULL UNIQUE,
  issued_at            DATE          NOT NULL,
  issuing_authority    VARCHAR(256)  NOT NULL,
  status               VARCHAR(16)   NOT NULL,                -- VALID | EXPIRED | REVOKED

  -- Subject snapshot (redacted in preview DTO, revealed in full DTO)
  subject_full_name    VARCHAR(128)  NOT NULL,
  subject_dob          DATE          NOT NULL,
  subject_citizen_id   VARCHAR(20)   NOT NULL,
  subject_address      VARCHAR(256)  NOT NULL,

  body                 CLOB          NOT NULL,                -- long-form content
  search_text          VARCHAR(1024) NOT NULL,                -- NFD-folded, diacritic-stripped concat

  created_at           TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at           TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_document_owner FOREIGN KEY (owner_id)
    REFERENCES app_user(id) ON DELETE RESTRICT
);

CREATE INDEX idx_document_type        ON document(type);
CREATE INDEX idx_document_status      ON document(status);
CREATE INDEX idx_document_search_text ON document(search_text);

-- Attachments (metadata only — no real file storage in prototype)
CREATE TABLE document_attachment (
  id            UUID          PRIMARY KEY,
  document_id   UUID          NOT NULL,
  file_name     VARCHAR(256)  NOT NULL,
  mime_type     VARCHAR(64)   NOT NULL,
  size_bytes    BIGINT        NOT NULL,
  sort_order    INT           NOT NULL DEFAULT 0,
  CONSTRAINT fk_attachment_document FOREIGN KEY (document_id)
    REFERENCES document(id) ON DELETE CASCADE
);

CREATE INDEX idx_attachment_document ON document_attachment(document_id);

-- Short-lived codes for mock VNeID authorize→token exchange
CREATE TABLE oauth_auth_code (
  code           VARCHAR(64)   PRIMARY KEY,
  user_id        UUID          NOT NULL,
  redirect_uri   VARCHAR(512)  NOT NULL,
  state          VARCHAR(128),
  expires_at     TIMESTAMP     NOT NULL,
  consumed       BOOLEAN       NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_auth_code_user FOREIGN KEY (user_id)
    REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE INDEX idx_auth_code_expires ON oauth_auth_code(expires_at);
```

### 5.2 Entity relationship overview

```
┌──────────────┐ 1       1 ┌─────────────┐ 1      0..* ┌─────────────────────┐
│   app_user   │──────────▶│  document   │────────────▶│ document_attachment │
└──────┬───────┘           └─────────────┘             └─────────────────────┘
       │ 1
       │ 0..*
       ▼
┌──────────────────┐
│ oauth_auth_code  │
└──────────────────┘
```

Cardinalities:
- `app_user` **1 ↔ 1** `document` (enforced via `document.owner_id UNIQUE`)
- `document` **1 → 0..\*** `document_attachment`
- `app_user` **1 → 0..\*** `oauth_auth_code`

### 5.3 JPA entity sketch

| Entity | Table | Key relationships |
|---|---|---|
| `User` | `app_user` | `@OneToOne(mappedBy="owner") Document document` |
| `Document` | `document` | `@OneToOne @JoinColumn(name="owner_id") User owner`; `@OneToMany(mappedBy="document") List<DocumentAttachment> attachments` |
| `DocumentAttachment` | `document_attachment` | `@ManyToOne Document document` |
| `OAuthAuthCode` | `oauth_auth_code` | `@ManyToOne User user` |

- Enums (`DocumentType`, `DocumentStatus`) persisted as `@Enumerated(EnumType.STRING)`.
- UUIDs generated application-side via `UUID.randomUUID()` — for seed data, committed literals in `V2__seed.sql` keep QR codes stable across restarts.
- Spring Data repositories: `UserRepository`, `DocumentRepository`, `DocumentAttachmentRepository`, `OAuthAuthCodeRepository`.

### 5.4 Preview vs full projection

Projection is a **DTO-layer concern**, not a schema concern. Two DTOs over the same `Document` row:

- `DocumentPreviewDto` — `id`, `type`, `title`, `documentNumber`, `issuedAt`, `issuingAuthority`, `status`, `subjectFullNameMasked`
- `DocumentFullDto` — above plus `subjectFullName`, `subjectDob`, `subjectCitizenId`, `subjectAddress`, `body`, `attachments[]`

`DocumentService#canViewFull(currentUser, document)` picks which DTO `DocumentMapper` emits per request.

---

## 6. REST API

All paths prefixed `/api`. Auth: `Authorization: Bearer <jwt>` when logged in, omitted when guest.

| Method | Path | Auth | Returns |
|---|---|---|---|
| GET  | `/documents/search?q=&type=&page=&size=` | optional | Paginated **preview** list. `q` matched against `search_text` (diacritic-insensitive). |
| GET  | `/documents/{id}` | optional | Preview OR full, depending on auth + ownership |
| GET  | `/documents/{id}/qr.png` | public | 512×512 PNG encoding `${PUBLIC_BASE_URL}/search?id={id}` |
| GET  | `/auth/mock-vneid/authorize?redirect_uri=&state=` | none | Renders mock VNeID picker (HTML) |
| POST | `/auth/mock-vneid/token` | none | `{ code } → { accessToken, user }` |
| GET  | `/auth/me` | required | Current user profile |

**Error shape:** `{ "error": "FORBIDDEN" | "NOT_FOUND" | "INVALID_TOKEN", "message": "..." }`.

---

## 7. Mock VNeID flow

Simulates a real OAuth authorization-code flow so the Angular code is shaped like it would be against real VNeID later. Auth codes persist briefly in `oauth_auth_code` (5-minute TTL).

```
[Angular]  "Đăng nhập" click
    → window.location = /api/auth/mock-vneid/authorize?redirect_uri=/auth/callback&state=<nonce>
[Backend]  Renders a simple HTML page: "Chọn tài khoản VNeID mô phỏng"
           listing the 8 seeded citizens as clickable cards.
[User]     Clicks a card.
[Backend]  INSERT oauth_auth_code (code, user_id, redirect_uri, state, expires_at);
           302 → redirect_uri?code=<code>&state=<nonce>
[Angular]  /auth/callback reads ?code, POSTs /api/auth/mock-vneid/token
[Backend]  SELECT + mark consumed; validates not-expired, not-consumed, matching redirect_uri;
           issues JWT (sub = User.id, 2h TTL) → returns JWT + user profile
[Angular]  Stores JWT in memory (not localStorage), navigates back to last route.
```

---

## 8. QR code flow end-to-end

1. Backend generates QR on demand via `/api/documents/{id}/qr.png`. QR payload is the full deep link: `https://<host>/search?id=<docId>`.
2. On startup, a `DemoQrExporter` (optional bean, activates via `app.export-qr=true`) writes every document's QR to `./seed-output/qr/{docId}.png` so they can be printed for the demo.
3. iPhone camera scan → Safari opens the URL → Angular `/search` route activates.
4. `SearchPageComponent` reads `id` query param on init:
   - sets the search input value
   - immediately calls `DocumentService.getById(id)`
   - on success, renders the document detail inline (preview vs full decided by backend response)
5. If no `id` param but `q` is present → full-text search.
6. If neither → empty state (matches screenshot — just the search bar and three feature buttons).

---

## 9. Angular app shell

**Routes**
- `/` — Home (header + nav + search bar + 3 feature buttons, exactly matching screenshot)
- `/search` — same layout as home but with results panel below the bar. Reads `?q=` and `?id=` query params.
- `/documents/:id` — full detail page (used when user clicks a result)
- `/auth/callback` — VNeID token exchange
- `/payments`, `/feedback`, `/help` — static placeholder pages (nav items from screenshot)

**Components**
- `HeaderComponent`, `NavBarComponent`, `SearchBarComponent`, `FeatureButtonsComponent`
- `SearchResultsComponent` — preview cards
- `DocumentDetailComponent` — renders preview or full based on response shape; shows a "Đăng nhập để xem đầy đủ" CTA when preview
- `AuthService` (JWT in memory, `currentUser$` signal), `AuthInterceptor`

**Visual targets (from screenshot)**
- Title in dark maroon (`#8B1C1C`-ish); tagline italic dark red
- Nav: light grey-beige, home icon in ochre square
- Feature buttons: ochre/yellow `#D9A441`-ish, white text, rounded
- System sans-serif font

---

## 10. Mock data (seeded via `data.sql`)

**20 users**, each owning exactly one document. Distribution chosen so search + status UI both have something to chew on (duplicate types exercise search relevance; non-VALID statuses exercise status badges):

| Document type | Count | Status breakdown |
|---|---|---|
| Giấy khai sinh (BIRTH_CERT) | 4 | 4 VALID |
| Giấy đăng ký kết hôn (MARRIAGE_CERT) | 4 | 4 VALID |
| Giấy phép kinh doanh (BUSINESS_REG) | 4 | 2 VALID, 1 EXPIRED, 1 REVOKED |
| Giấy phép lái xe (DRIVER_LICENSE) | 4 | 2 VALID, 1 EXPIRED, 1 REVOKED |
| Giấy CN quyền SD đất (LAND_USE_RIGHT) | 4 | 3 VALID, 1 EXPIRED |
| **Total** | **20** | **15 VALID, 3 EXPIRED, 2 REVOKED** |

Anchor demo users (the other 16 are filler with plausible Vietnamese names + addresses):

| User | Doc type | Status | Demo role |
|---|---|---|---|
| Nguyễn Văn An | BIRTH_CERT | VALID | Primary QR demo |
| Trần Thị Bình | MARRIAGE_CERT | VALID | Secondary demo |
| Đặng Văn Giang | BUSINESS_REG | EXPIRED | Expired-state UI |
| Bùi Thị Hương | DRIVER_LICENSE | REVOKED | Revoked-state UI |

- UUIDs are literal constants in `data.sql` → stable QR codes across restarts.
- Each document gets 2–3 mock rows in `document_attachment` (e.g. `giay-khai-sinh-ban-chinh.pdf`, `phu-luc-1.pdf`).
- `search_text` column precomputed in the SQL as a diacritic-folded concatenation of public fields.

---

## 11. Repository layout

```
DocumentSearch/
├── PLAN.md                          (this file)
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/viettel/docsearch/
│       │   ├── DocumentSearchApplication.java
│       │   ├── config/              (SecurityConfig, JpaConfig, CorsConfig)
│       │   ├── domain/              (User, Document, DocumentAttachment, OAuthAuthCode, enums)
│       │   ├── repo/                (Spring Data JPA interfaces)
│       │   ├── search/              (SearchTextFolder — NFD + diacritic strip)
│       │   ├── service/             (DocumentService, AuthService, QrService, MockVneidService)
│       │   ├── web/                 (controllers, dto/, mapper/)
│       │   └── demo/                (DemoQrExporter — optional)
│       └── resources/
│           ├── application.yml
│           ├── schema.sql
│           ├── data.sql
│           └── templates/mock-vneid-picker.html
├── frontend/
│   ├── package.json
│   ├── angular.json
│   └── src/app/
│       ├── app.routes.ts
│       ├── core/ (auth, interceptor, api client)
│       ├── shared/ (header, nav, search-bar, feature-buttons)
│       └── pages/ (home, search, document-detail, auth-callback, placeholder)
├── data/                            (H2 file DB — gitignored)
└── seed-output/qr/                  (generated QR PNGs for demo — gitignored)
```

---

## 12. Milestones

Each milestone is independently demoable.

**M1 — Backend skeleton + relational schema + seed + search**
- Spring Boot app boots
- H2 at `./data/docsearch.mv.db`, `/h2-console` enabled
- `schema.sql` then `data.sql` run on startup → 20 users, 20 documents, ~50 attachments
- `spring.jpa.hibernate.ddl-auto=none` and `spring.sql.init.mode=always` so our SQL (not Hibernate) is authoritative for the schema
- Spring Data JPA repos wired
- `GET /api/documents/search?q=` returns preview list (diacritic-insensitive match against `search_text`)
- `GET /api/documents/{id}` returns preview (gating deferred to M3)
- Verified via `curl` + live SQL in H2 console

**M2 — QR generation**
- `GET /api/documents/{id}/qr.png` returns a scannable PNG
- `app.export-qr=true` startup flag dumps all 8 QR PNGs to `seed-output/qr/`
- Verified by scanning one with a phone → lands on `http://<host>:4200/search?id=<id>`

**M3 — Mock VNeID + JWT + full-view gating**
- Mock VNeID picker page (reads `app_user` table)
- `oauth_auth_code` table used for code issuance + single-use consumption
- JWT issuance + validation
- `GET /api/documents/{id}` returns full for owner, preview otherwise
- `GET /api/auth/me`
- Verified via Postman/curl

**M4 — Angular shell matching the screenshot**
- Static home page pixel-close to screenshot
- All nav routes exist (placeholders for non-core)
- No backend calls yet

**M5 — Search wiring**
- Search bar submits → `/search?q=...` → result list
- Click result → `/documents/:id` (preview since still guest)

**M6 — QR deep-link behavior**
- `/search?id=<docId>` auto-prefills, auto-fetches, shows preview inline
- End-to-end phone-scan demo works

**M7 — Login flow + full view**
- "Đăng nhập" → mock VNeID redirect → JWT back
- Owner sees full fields; non-owner still sees preview
- "Đăng nhập để xem đầy đủ" CTA on preview

**M8 — Polish**
- Loading / empty / error states
- README with run instructions + demo script
- Printable sheet of all 8 QR codes

---

## 13. Key design decisions & tradeoffs

1. **H2 file mode + `schema.sql` / `data.sql`** over any in-memory option. Real SQL, `/h2-console` for live demos, zero migration-framework overhead for a 20-row prototype. Flyway is easy to layer on later if the schema starts evolving. Postgres swap is a URL change (`jdbc:h2:...;MODE=PostgreSQL` → real Postgres). Cost: ~a few hundred ms extra boot time vs a `HashMap`.
2. **Diacritic-insensitive search via a pre-folded `search_text` column + `LIKE`.** Keeps H2 usable for Vietnamese text without pulling in Lucene / Hibernate Search. Trade: no ranking, no stemming — acceptable for 8-ish records. `SearchService` is the single swap-point if we upgrade later.
3. **JWT in memory (Angular), not localStorage.** Prototype-friendly and avoids XSS footgun. Trade: token lost on reload — acceptable.
4. **OAuth-shaped mock VNeID** with a real `oauth_auth_code` table instead of a simple dropdown. The Angular code matches reality; the real VNeID swap is URL/config-level.
5. **Deterministic seed UUIDs committed into SQL.** Keeps printed QR codes valid forever.
6. **No user registration.** Users exist only as seeded VNeID identities. Matches real VNeID's "identity from outside" model.
7. **Attachments as a separate table, no file storage.** Correct relational shape now; swapping to real S3/disk later touches only `QrService`-adjacent code, not the schema.

---

## 14. Open questions / assumptions to confirm

- [ ] **Full-view gating** — confirm: logged-in user sees full only for their own doc? Default: **their own only**.
- [ ] **QR payload host** — for phone scanning on the same Wi-Fi, QRs must encode the machine's LAN IP, not `localhost`. Planned: `app.public-base-url` in `application.yml`, default `http://localhost:4200`, override via env var for demos.
- [ ] **Vietnamese-only UI** — confirmed from screenshot. No English toggle planned.
- [ ] **Advanced search modal** ("Tìm kiếm nâng cao") — out of scope; clicking will show "Sắp ra mắt".
- [ ] **Masking format** for subject name on preview — default: `Nguyễn V*** A` (first + last initial, middle masked).

Reply to any of these and I'll adjust before we start M1.
