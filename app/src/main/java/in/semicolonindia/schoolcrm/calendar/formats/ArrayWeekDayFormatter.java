package in.semicolonindia.schoolcrm.calendar.formats;

/**
 * Created by Rupesh on 08-08-2017.
 */
@SuppressWarnings("ALL")
public class ArrayWeekDayFormatter implements WeekDayFormatter {

    private final CharSequence[] weekDayLabels;

    /**
     * @param weekDayLabels an array of 7 labels, starting with Sunday
     */
    public ArrayWeekDayFormatter(CharSequence[] weekDayLabels) {
        if (weekDayLabels == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        if (weekDayLabels.length != 7) {
            throw new IllegalArgumentException("Array must contain exactly 7 elements");
        }
        this.weekDayLabels = weekDayLabels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence format(int dayOfWeek) {
        return weekDayLabels[dayOfWeek - 1];
    }
}