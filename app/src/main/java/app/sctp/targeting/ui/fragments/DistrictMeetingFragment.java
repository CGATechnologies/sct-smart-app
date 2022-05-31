package app.sctp.targeting.ui.fragments;

import app.sctp.R;
import app.sctp.targeting.models.TargetingSession;

public class DistrictMeetingFragment extends TargetingMeetingBaseFragment {
    @Override
    protected String getSubtitle() {
        return null;
    }

    @Override
    protected int getMenu() {
        return R.menu.district_meeting_options;
    }

    @Override
    protected int getDownloadMenuOption() {
        return R.id.dmo_download_new_data;
    }

    @Override
    protected TargetingSession.MeetingPhase getMeetingPhase() {
        return TargetingSession.MeetingPhase.district_meeting;
    }
}
