-- ============================================================================
-- Oracle 11g schema (used by the 'oracle' Spring profile).
-- Differences vs the H2 schema.sql:
--   * UUID            -> VARCHAR2(36)  (Oracle has no UUID type; UUIDs are stored as
--                        their 36-char string form. Hibernate is told to map UUID to
--                        CHAR via hibernate.type.preferred_uuid_jdbc_type=CHAR.)
--   * BOOLEAN         -> NUMBER(1)     (Oracle 11g has no SQL BOOLEAN type)
--   * BIGINT / INT    -> NUMBER(19) / NUMBER(10)
--   * VARCHAR(8000)   -> VARCHAR2(4000) (Oracle 11g VARCHAR2 max is 4000 bytes;
--                        seed bodies are < 400 chars and documents are read-only)
--   * DROP TABLE IF EXISTS -> DROP TABLE ... CASCADE CONSTRAINTS (no IF EXISTS in 11g;
--                        the first-run "table does not exist" error is tolerated via
--                        spring.sql.init.continue-on-error=true in application-oracle.yml)
--   * ON DELETE RESTRICT removed (not valid Oracle syntax; no-action is the default)
-- ============================================================================

DROP TABLE oauth_auth_code    CASCADE CONSTRAINTS;
DROP TABLE document_attachment CASCADE CONSTRAINTS;
DROP TABLE document            CASCADE CONSTRAINTS;
DROP TABLE app_user            CASCADE CONSTRAINTS;

CREATE TABLE app_user (
  id              VARCHAR2(36)   PRIMARY KEY,
  vneid_subject   VARCHAR2(64)   NOT NULL UNIQUE,
  full_name       VARCHAR2(128)  NOT NULL,
  dob             DATE           NOT NULL,
  citizen_id      VARCHAR2(20)   NOT NULL UNIQUE,
  address         VARCHAR2(256)  NOT NULL,
  account_type    VARCHAR2(16)   DEFAULT 'CITIZEN' NOT NULL,
  created_at      TIMESTAMP      DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- NOTE: the LLTP tables (DECLARATION / SYNTHESIS_VERIFICATION / JUDGMENT / BAN_POSITION)
-- are NOT created here — under the 'oracle' profile they are the real DBDaotao tables.
-- The H2 schema.sql mirrors them only for the zero-setup demo.

CREATE TABLE document (
  id                   VARCHAR2(36)   PRIMARY KEY,
  owner_id             VARCHAR2(36)   NOT NULL UNIQUE,
  type                 VARCHAR2(32)   NOT NULL,
  title                VARCHAR2(256)  NOT NULL,
  document_number      VARCHAR2(64)   NOT NULL UNIQUE,
  issued_at            DATE           NOT NULL,
  issuing_authority    VARCHAR2(256)  NOT NULL,
  status               VARCHAR2(16)   NOT NULL,
  subject_full_name    VARCHAR2(128)  NOT NULL,
  subject_dob          DATE           NOT NULL,
  subject_citizen_id   VARCHAR2(20)   NOT NULL,
  subject_address      VARCHAR2(256)  NOT NULL,
  body                 VARCHAR2(4000) NOT NULL,
  search_text          VARCHAR2(1024) NOT NULL,
  created_at           TIMESTAMP      DEFAULT CURRENT_TIMESTAMP NOT NULL,
  updated_at           TIMESTAMP      DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT fk_document_owner FOREIGN KEY (owner_id) REFERENCES app_user(id)
);

CREATE INDEX idx_document_type        ON document(type);
CREATE INDEX idx_document_status      ON document(status);
CREATE INDEX idx_document_search_text ON document(search_text);

CREATE TABLE document_attachment (
  id            VARCHAR2(36)   PRIMARY KEY,
  document_id   VARCHAR2(36)   NOT NULL,
  file_name     VARCHAR2(256)  NOT NULL,
  mime_type     VARCHAR2(64)   NOT NULL,
  size_bytes    NUMBER(19)     NOT NULL,
  sort_order    NUMBER(10)     DEFAULT 0 NOT NULL,
  CONSTRAINT fk_attachment_document FOREIGN KEY (document_id)
    REFERENCES document(id) ON DELETE CASCADE
);

CREATE INDEX idx_attachment_document ON document_attachment(document_id);

CREATE TABLE oauth_auth_code (
  code           VARCHAR2(64)   PRIMARY KEY,
  user_id        VARCHAR2(36)   NOT NULL,
  redirect_uri   VARCHAR2(512)  NOT NULL,
  state          VARCHAR2(128),
  expires_at     TIMESTAMP      NOT NULL,
  consumed       NUMBER(1)      DEFAULT 0 NOT NULL,
  CONSTRAINT fk_auth_code_user FOREIGN KEY (user_id)
    REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE INDEX idx_auth_code_expires ON oauth_auth_code(expires_at);