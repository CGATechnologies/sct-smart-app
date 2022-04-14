package app.sctp.targeting.models;

import java.util.List;

public class PreEligibilityVerificationSessionResponse {
    private int page;
    private int totalPages;
    private List<PreEligibilityVerificationSession> sessions;

    public List<PreEligibilityVerificationSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<PreEligibilityVerificationSession> sessions) {
        this.sessions = sessions;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLastPage(){
        return getPage() == getTotalPages();
    }
}
