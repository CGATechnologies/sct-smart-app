package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

import java.util.Locale;

@DatabaseView("select p.*, (select coalesce(count(householdId), 0) from households where sessionId = p.id and selection = 'Selected') selectedHouseholds from pev_sessions p")
public class SessionView extends PreEligibilityVerificationSession {
    @ColumnInfo
    private Integer selectedHouseholds;

    public Integer getSelectedHouseholds() {
        return selectedHouseholds;
    }

    public void setSelectedHouseholds(Integer selectedHouseholds) {
        this.selectedHouseholds = selectedHouseholds;
    }

    public String getHouseholdCountSummary() {
        return String.format(Locale.US, "%,d household%s",
                getHouseholds(), getHouseholds() != 1 ? 's' : "");
    }
}
