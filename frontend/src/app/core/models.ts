export interface UserDto {
  id: string;
  fullName: string;
  dob: string;
  citizenId: string;
  address: string;
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
