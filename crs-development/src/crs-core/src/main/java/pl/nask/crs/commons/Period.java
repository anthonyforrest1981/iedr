package pl.nask.crs.commons;

/*>>>import org.checkerframework.checker.nullness.qual.PolyNull;*/

public class Period {

    private int months;

    private Period(int months) {
        this.months = months;
    }

    public static /*>>>@PolyNull*/ Period fromYears(/*>>>@PolyNull*/ Integer years) {
        if (years == null) {
            return null;
        } else {
            return new Period(12 * years);
        }
    }

    public static /*>>>@PolyNull*/ Period fromMonths(/*>>>@PolyNull*/ Integer months) {
        if (months == null) {
            return null;
        } else {
            return new Period(months);
        }
    }

    public int getMonths() {
        return months;
    }

    public int getYears() {
        if (months % 12 != 0) {
            throw new IllegalStateException("Couldn't calculate years from " + months);
        } else {
            return months / 12;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + months;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Period other = (Period) obj;
        if (months != other.months)
            return false;
        return true;
    }

}
