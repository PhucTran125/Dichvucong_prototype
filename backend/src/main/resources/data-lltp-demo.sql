-- ============================================================================
-- LLTP (Phiếu lý lịch tư pháp) demo certificates — Điều 17 verification.
-- Loaded ONLY by the default (H2) profile via spring.sql.init.data-locations.
-- Under the 'oracle' profile the real DBDaotao tables provide the certificates.
--   #1 (055240000001) Nguyễn Văn An  — KHÔNG có án tích
--   #2 (055240000002) Trần Thị Bình  — CÓ án tích (1 bản án + 1 cấm đảm nhiệm chức vụ)
--   #3 (055240000003) Lê Quốc Cường  — KHÔNG có án tích (cơ quan cấp: Sở Tư pháp TP.HCM)
-- Auth codes (mã xác thực) are derived; list them via GET /api/lltp/_demo/codes.
-- ============================================================================
INSERT INTO declaration (declaration_id, receive_no, full_name, gender_id, birth_date, identify_no, justice_no, residence, birth_place, nationalyty_name, ethnic_name, ministry_justice_id, is_active) VALUES
(5001, 'PLT-2024-0001', 'Nguyễn Văn An',  1, DATE '1990-03-15', '001090000001', 'LLTP-2024-0001', 'Số 12, phố Hàng Bài, quận Hoàn Kiếm, Hà Nội',        'Hà Nội',   'Việt Nam', 'Kinh', 2, '1');
INSERT INTO declaration (declaration_id, receive_no, full_name, gender_id, birth_date, identify_no, justice_no, residence, birth_place, nationalyty_name, ethnic_name, ministry_justice_id, is_active) VALUES
(5002, 'PLT-2024-0002', 'Trần Thị Bình',  0, DATE '1985-07-22', '001085000002', 'LLTP-2024-0002', 'Số 45, đường Trần Hưng Đạo, quận Hai Bà Trưng, Hà Nội', 'Nam Định', 'Việt Nam', 'Kinh', 2, '1');
INSERT INTO declaration (declaration_id, receive_no, full_name, gender_id, birth_date, identify_no, justice_no, residence, birth_place, nationalyty_name, ethnic_name, ministry_justice_id, is_active) VALUES
(5003, 'PLT-2024-0003', 'Lê Quốc Cường',  1, DATE '1982-11-05', '001082000003', 'LLTP-2024-0003', 'Số 78, phố Láng Hạ, quận Đống Đa, Hà Nội',           'Hà Nội',   'Việt Nam', 'Kinh', 3, '1');

INSERT INTO synthesis_verification (synthesis_verification_id, document_no, issue_date, issue_person, synthesis_date, ministry_justice_id, status_crime_id, ban_possition_status_id, user_signed, is_active, declaration_id) VALUES
(9001, '055240000001', DATE '2024-06-01', 'Nguyễn Thị Hồng', DATE '2024-05-30', 2, 1, 1, 'Trung tâm Lý lịch tư pháp quốc gia',       '1', 5001);
INSERT INTO synthesis_verification (synthesis_verification_id, document_no, issue_date, issue_person, synthesis_date, ministry_justice_id, status_crime_id, ban_possition_status_id, user_signed, is_active, declaration_id) VALUES
(9002, '055240000002', DATE '2024-06-10', 'Phạm Văn Đức',    DATE '2024-06-08', 2, 2, 2, 'Sở Tư pháp thành phố Hà Nội',              '1', 5002);
INSERT INTO synthesis_verification (synthesis_verification_id, document_no, issue_date, issue_person, synthesis_date, ministry_justice_id, status_crime_id, ban_possition_status_id, user_signed, is_active, declaration_id) VALUES
(9003, '055240000003', DATE '2024-07-02', 'Trần Thị Mai',    DATE '2024-06-30', 3, 1, 1, 'Sở Tư pháp Thành phố Hồ Chí Minh',         '1', 5003);

INSERT INTO judgment (judgment_id, judgment_no, judgment_date, court_id, crime_names, penalty_main, penalty_add, terms, exec_judgment_status, remission_date, ministry_justice_id, is_active, declaration_id) VALUES
(7001, '15/2018/HSST', DATE '2018-04-12', 3, 'Tội trộm cắp tài sản', 'Phạt tù 18 tháng', 'Không', 'Điều 173 Bộ luật Hình sự', 'Đã chấp hành xong hình phạt', DATE '2021-05-01', 2, '1', 5002);

INSERT INTO ban_position (ban_position_id, ban_position, decision_no, decision_date, court_id, from_date, ministry_justice_id, is_active, declaration_id) VALUES
(8001, 'Cấm đảm nhiệm chức vụ quản lý doanh nghiệp', '15/2018/HSST', DATE '2018-04-12', 3, DATE '2018-10-12', 2, '1', 5002);
