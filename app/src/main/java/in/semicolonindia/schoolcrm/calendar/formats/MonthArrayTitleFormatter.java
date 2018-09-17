package in.semicolonindia.schoolcrm.calendar.formats;

import android.text.SpannableStringBuilder;

import in.semicolonindia.schoolcrm.calendar.CalendarDay;


/**
 * Created by Rupesh on 08-08-2017.
 */
@SuppressWarnings("ALL")
public class MonthArrayTitleFormatter implements TitleFormatter {

    private final CharSequence[] monthLabels;

    /**
     * Format using an array of month labels
     *
     * @param monthLabels an array of 12 labels to use for months, starting with January
     */
    public MonthArrayTitleFormatter(CharSequence[] monthLabels) {
        if (monthLabels == null) {
            throw new IllegalArgumentException("Label array cannot be null");
        }
        if (monthLabels.length < 12) {
            throw new IllegalArgumentException("Label array is too short");
        }
        this.monthLabels = monthLabels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence format(CalendarDay day) {
        return new SpannableStringBuilder()
                .append(monthLabels[day.getMonth()])
                .append(" ")
                .append(String.valueOf(day.getYear()));
    }
}
