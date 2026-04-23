DROP TABLE IF EXISTS oauth_auth_code;
DROP TABLE IF EXISTS document_attachment;
DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS app_user;

CREATE TABLE app_user (
  id              UUID          PRIMARY KEY,
  vneid_subject   VARCHAR(64)   NOT NULL UNIQUE,
  full_name       VARCHAR(128)  NOT NULL,
  dob             DATE          NOT NULL,
  citizen_id      VARCHAR(20)   NOT NULL UNIQUE,
  address         VARCHAR(256)  NOT NULL,
  created_at      TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE document (
  id                   UUID          PRIMARY KEY,
  owner_id             UUID          NOT NULL UNIQUE,
  type                 VARCHAR(32)   NOT NULL,
  title                VARCHAR(256)  NOT NULL,
  document_number      VARCHAR(64)   NOT NULL UNIQUE,
  issued_at            DATE          NOT NULL,
  issuing_authority    VARCHAR(256)  NOT NULL,
  status               VARCHAR(16)   NOT NULL,
  subject_full_name    VARCHAR(128)  NOT NULL,
  subject_dob          DATE          NOT NULL,
  subject_citizen_id   VARCHAR(20)   NOT NULL,
  subject_address      VARCHAR(256)  NOT NULL,
  body                 VARCHAR(8000) NOT NULL,
  search_text          VARCHAR(1024) NOT NULL,
  created_at           TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at           TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_document_owner FOREIGN KEY (owner_id)
    REFERENCES app_user(id) ON DELETE RESTRICT
);

CREATE INDEX idx_document_type        ON document(type);
CREATE INDEX idx_document_status      ON document(status);
CREATE INDEX idx_document_search_text ON document(search_text);

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
