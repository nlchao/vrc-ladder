package ca.sfu.cmpt373.alpha.vrcladder.teams;

import ca.sfu.cmpt373.alpha.vrcladder.users.User;

/**
 * Created by Trevor on 5/26/2016.
 * A class that acts as a container for team members
 */
public class Team {
    //teams should be strictly two members, not an arbitrary size
    private final User member1;
    private final User member2;

    //TODO: decide whether we should add a ranking member variable, or just rely on the database to return teams in ranked order
    private final AttendanceInfo attendanceInfo;

    public Team(User member1, User member2, AttendanceInfo attendanceInfo) {
        this.member1 = member1;
        this.member2 = member2;
        this.attendanceInfo = attendanceInfo;
    }

    public User getMember1() {
        return member1;
    }

    public User getMember2() {
        return member2;
    }

    public AttendanceInfo getAttendanceInfo() {
        return attendanceInfo;
    }
}
