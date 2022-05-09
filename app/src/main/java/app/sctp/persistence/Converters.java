package app.sctp.persistence;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import app.sctp.targeting.models.ChronicIllness;
import app.sctp.targeting.models.Disability;
import app.sctp.targeting.models.EducationLevel;
import app.sctp.targeting.models.Gender;
import app.sctp.targeting.models.GradeLevel;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.models.MaritalStatus;
import app.sctp.targeting.models.Orphanhood;
import app.sctp.targeting.models.RelationshipToHead;
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.utils.LocaleUtils;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    @TypeConverter
    public static List<Long> toList(String commaSeparatedList) {
        if (LocaleUtils.hasText(commaSeparatedList)) {
            String[] items = commaSeparatedList.split(",");
            if (items.length > 0) {
                List<Long> list = new LinkedList<>();
                for (String number : items) {
                    list.add(Long.parseLong(number));
                }
                return list;
            }
        }
        return Collections.emptyList();
    }

    @TypeConverter
    public static String toCommaSeparatedList(List<Long> longList) {
        if (longList == null || longList.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder(longList.get(0).toString());
        for (int i = 1; i < longList.size(); i++) {
            builder.append(",").append(longList.get(i).toString());
        }
        return builder.toString();
    }

    @TypeConverter
    public static LocationType locationType(String name) {
        if (LocaleUtils.hasText(name)) {
            return LocationType.valueOf(name);
        }
        return null;
    }

    @TypeConverter
    public static String locationTypeToString(LocationType locationType) {
        if (locationType != null) {
            return locationType.name();
        }
        return null;
    }

    @TypeConverter
    public static EducationLevel educationLevelFromString(String name) {
        return enumFromString(name, EducationLevel.class);
    }

    @TypeConverter
    public static String educationLevelToString(EducationLevel educationLevel) {
        return enumToString(educationLevel);
    }

    @TypeConverter
    public static ChronicIllness chronicIllnessFromString(String name) {
        return enumFromString(name, ChronicIllness.class);
    }

    @TypeConverter
    public static String chronicIllnessToString(ChronicIllness chronicIllness) {
        return enumToString(chronicIllness);
    }

    @TypeConverter
    public static Orphanhood orphanhoodFromString(String name) {
        return enumFromString(name, Orphanhood.class);
    }

    @TypeConverter
    public static String orphanhoodToString(Orphanhood orphanhood) {
        return enumToString(orphanhood);
    }

    @TypeConverter
    public static MaritalStatus maritalStatusFromName(String name) {
        return enumFromString(name, MaritalStatus.class);
    }

    @TypeConverter
    public static String maritalStatusToString(MaritalStatus maritalStatus) {
        return enumToString(maritalStatus);
    }

    @TypeConverter
    public static RelationshipToHead relationshipToHeadFromString(String name) {
        return enumFromString(name, RelationshipToHead.class);
    }

    @TypeConverter
    public static String relationshipToHeadToString(RelationshipToHead relationship) {
        return enumToString(relationship);
    }

    @TypeConverter
    public static GradeLevel gradeLevelFromString(String name) {
        return enumFromString(name, GradeLevel.class);
    }

    @TypeConverter
    public static String gradeLevelToString(GradeLevel gradeLevel) {
        return enumToString(gradeLevel);
    }

    @TypeConverter
    public static Disability disabilityFromString(String name) {
        return enumFromString(name, Disability.class);
    }

    @TypeConverter
    public static String disabilityToString(Disability disability) {
        return enumToString(disability);
    }

    @TypeConverter
    public static Gender genderFromString(String name) {
        return enumFromString(name, Gender.class);
    }

    @TypeConverter
    public static String genderToString(Gender gender) {
        return enumToString(gender);
    }

    @TypeConverter
    public static String selectionStatusToString(SelectionStatus selectionStatus) {
        return enumToString(selectionStatus);
    }

    @TypeConverter
    public static SelectionStatus selectionStatusFromString(String name) {
        return enumFromString(name, SelectionStatus.class);
    }

    private static String enumToString(Enum<?> tEnum) {
        return tEnum != null ? tEnum.name() : null;
    }

    private static <T extends Enum<T>> T enumFromString(String name, Class<T> clazz) {
        if (LocaleUtils.hasText(name)) {
            return Enum.valueOf(clazz, name);
        }
        return null;
    }
}
