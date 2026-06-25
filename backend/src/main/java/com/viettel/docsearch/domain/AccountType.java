package com.viettel.docsearch.domain;

/**
 * Account kind for a seeded identity: an individual citizen, an organization
 * (tài khoản định danh tổ chức), or an OFFICER (cán bộ) — the latter gates the
 * Điều 17 LLTP verification page.
 */
public enum AccountType {
    CITIZEN,
    ORG,
    OFFICER
}
