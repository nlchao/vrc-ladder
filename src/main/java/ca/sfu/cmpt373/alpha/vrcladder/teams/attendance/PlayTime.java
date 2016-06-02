package ca.sfu.cmpt373.alpha.vrcladder.teams.attendance;

public enum PlayTime {
    NONE(false),              // Not Attending
    TIME_SLOT_A(true),        // 8:00 PM
    TIME_SLOT_B(true);        // 9:30 PM

    Boolean isPlayable;

    PlayTime(boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    public boolean isPlayable() {
        return isPlayable;
    }
}
