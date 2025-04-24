package in.OAndM.utils;

public class DataTypeUtil {

    public static Class<?> inferType(String value) {
        if (value == null) {
            return String.class;
        }

        try {
            Integer.parseInt(value);
            return Integer.class;
        } catch (NumberFormatException e) {
            // Not an Integer
        }

        try {
            Long.parseLong(value);
            return Long.class;
        } catch (NumberFormatException e) {
            // Not a Long
        }

        try {
            Double.parseDouble(value);
            return Double.class;
        } catch (NumberFormatException e) {
            // Not a Double
        }

        // Default to String if no other type matches
        return String.class;
    }
}
