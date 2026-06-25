export type AccountType = 'CITIZEN' | 'ORG' | 'OFFICER';

export interface UserDto {
  id: string;
  fullName: string;
  dob: string;
  citizenId: string;
  address: string;
  accountType: AccountType;
}

export interface TokenResponse {
  accessToken: string;
  expiresInSeconds: number;
  user: UserDto;
}

export type DocumentStatus = 'VALID' | 'EXPIRED' | 'REVOKED';

export interface DocumentPreviewDto {
  id: string;
  type: string;
  typeLabel: string;
  title: string;
  documentNumber: string;
  issuedAt: string;
  issuingAuthority: string;
  status: DocumentStatus;
  subjectFullNameMasked: string;
  subjectCitizenId: string;
  preview: true;
}

export interface AttachmentDto {
  id: string;
  fileName: string;
  mimeType: string;
  sizeBytes: number;
}

export interface DocumentFullDto {
  id: string;
  type: string;
  typeLabel: string;
  title: string;
  documentNumber: string;
  issuedAt: string;
  issuingAuthority: string;
  status: DocumentStatus;
  subjectFullName: string;
  subjectDob: string;
  subjectCitizenId: string;
  subjectAddress: string;
  body: string;
  attachments: AttachmentDto[];
  preview: false;
}

export type DocumentDto = DocumentPreviewDto | DocumentFullDto;

export interface PageResponse<T> {
  items: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

// ---------------------------------------------------------------------------
// LLTP — Phiếu lý lịch tư pháp verification (Điều 17)
// ---------------------------------------------------------------------------
export interface CertificateSubjectDto {
  fullName: string;
  gender: string;
  dateOfBirth: string;
  identifyNo: string;
  nationality: string | null;
  ethnicity: string | null;
  residence: string;
  birthPlace: string;
}

export interface JudgmentDto {
  judgmentNo: string;
  judgmentDate: string;
  court: string;
  crimeNames: string;
  penaltyMain: string;
  penaltyAdd: string;
  terms: string;
  executionStatus: string;
  remissionDate: string | null;
}

export interface BanPositionDto {
  banPosition: string;
  decisionNo: string;
  decisionDate: string;
  court: string;
  fromDate: string;
}

export interface CertificateDto {
  certificateNumber: string;
  justiceNo: string;
  issueDate: string;
  issuePerson: string;
  issuingAgency: string;
  synthesisDate: string;
  digitallySigned: boolean;
  signedBy: string | null;
  subject: CertificateSubjectDto;
  crimeStatus: string;
  hasCriminalRecord: boolean;
  banPositionStatus: string;
  judgments: JudgmentDto[];
  banPositions: BanPositionDto[];
}

export interface VerifyResultDto {
  valid: boolean;
  verifiedVia: 'FIELDS' | 'QR' | null;
  message: string | null;
  certificate: CertificateDto | null;
}

export interface VerifyByFieldsRequest {
  certificateNumber: string;
  fullName: string;
  dateOfBirth: string;
  gender: string;
}

export interface OfficerLoginRequest {
  username: string;
  password: string;
}

export interface DemoCertificateCode {
  certificateNumber: string;
  fullName: string;
  dateOfBirth: string;
  gender: string;
  identifyNo: string;
  issueDate: string;
  crimeStatus: string;
  authCode: string;
  qrSignature: string;
  verifyQrUrl: string;
}

/** Result of the dev/test QR generator (GET /api/lltp/_demo/qr). */
export interface QrTestResult {
  found: boolean;
  receiveNo: string;
  deepLink: string | null;
  qrPngUrl: string | null;
}
