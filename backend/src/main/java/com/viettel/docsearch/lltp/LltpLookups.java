package com.viettel.docsearch.lltp;

import com.viettel.docsearch.search.SearchTextFolder;

import java.util.Map;

/**
 * Lightweight label lookups for the foreign-key codes carried on the LLTP tables
 * ({@code GENDER_ID}, {@code MINISTRY_JUSTICE_ID}, {@code STATUS_CRIME_ID}, ...).
 *
 * <p>In production these resolve against the system's category tables; for the prototype we
 * map the seeded ids to human labels here. IDs match the rows seeded in {@code data.sql}.
 */
public final class LltpLookups {

    private LltpLookups() {}

    // ----- Gender (GENDER_ID: 1 = Nam, 0 = Nữ) --------------------------------------------------
    public static final long GENDER_MALE = 1L;
    public static final long GENDER_FEMALE = 0L;

    public static String genderLabel(Long genderId) {
        if (genderId == null) return "";
        if (genderId == GENDER_MALE) return "Nam";
        if (genderId == GENDER_FEMALE) return "Nữ";
        return "Khác";
    }

    /**
     * True when the supplied gender matches the stored {@code GENDER_ID}. The UI sends the raw id
     * (0 = Nữ, 1 = Nam), which is compared directly; word forms ("Nam"/"Nữ"/"male"/"female"/"m"/"f")
     * are also accepted as a fallback.
     */
    public static boolean genderMatches(Long genderId, String supplied) {
        if (genderId == null || supplied == null || supplied.isBlank()) return false;
        String f = SearchTextFolder.fold(supplied.trim()); // accent-insensitive: "nữ" -> "nu"
        try {
            return genderId.equals(Long.parseLong(f)); // UI sends the raw GENDER_ID (0 / 1)
        } catch (NumberFormatException ignored) {
            // not numeric — fall back to word forms
        }
        Long asId = switch (f) {
            case "nam", "male", "m" -> GENDER_MALE;
            case "nu", "female", "f" -> GENDER_FEMALE;
            default -> null;
        };
        return genderId.equals(asId);
    }

    // ----- Issuing agency (cơ quan cấp Phiếu) ---------------------------------------------------
    private static final Map<Long, String> AGENCIES = Map.of(
        1L, "Trung tâm Lý lịch tư pháp quốc gia",
        2L, "Sở Tư pháp thành phố Hà Nội",
        3L, "Sở Tư pháp Thành phố Hồ Chí Minh",
        4L, "Sở Tư pháp thành phố Đà Nẵng"
    );

    public static String issuingAgency(long ministryJusticeId) {
        return AGENCIES.getOrDefault(ministryJusticeId, "Cơ quan quản lý cơ sở dữ liệu lý lịch tư pháp");
    }

    // ----- Án tích (criminal-record status) -----------------------------------------------------
    public static final long CRIME_STATUS_CLEAR = 1L;
    public static final long CRIME_STATUS_HAS_RECORD = 2L;

    public static String crimeStatusLabel(Long statusCrimeId) {
        if (statusCrimeId != null && statusCrimeId == CRIME_STATUS_HAS_RECORD) {
            return "Có án tích";
        }
        return "Không có án tích";
    }

    public static boolean hasCriminalRecord(Long statusCrimeId) {
        return statusCrimeId != null && statusCrimeId == CRIME_STATUS_HAS_RECORD;
    }

    // ----- Cấm đảm nhiệm chức vụ ----------------------------------------------------------------
    public static String banPositionStatusLabel(Long banPositionStatusId) {
        if (banPositionStatusId != null && banPositionStatusId == 2L) {
            return "Bị cấm đảm nhiệm chức vụ, thành lập, quản lý doanh nghiệp, hợp tác xã";
        }
        return "Không bị cấm đảm nhiệm chức vụ, thành lập, quản lý doanh nghiệp, hợp tác xã";
    }

    // ----- Tòa án -------------------------------------------------------------------------------
    private static final Map<Long, String> COURTS = Map.of(
        1L, "Tòa án nhân dân thành phố Hà Nội",
        2L, "Tòa án nhân dân Thành phố Hồ Chí Minh",
        3L, "Tòa án nhân dân quận Hoàn Kiếm, Hà Nội",
        4L, "Tòa án nhân dân quận Đống Đa, Hà Nội"
    );

    public static String courtName(Long courtId) {
        if (courtId == null) return "";
        return COURTS.getOrDefault(courtId, "Tòa án nhân dân (mã " + courtId + ")");
    }
}
