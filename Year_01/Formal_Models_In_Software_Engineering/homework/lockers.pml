mtype = {
    requestPickup,
    pickupAuthorized,
    unlockLocker,
    lockerOpened,
    retrieveParcel,
    lockerClosed
};
chan user_sys   = [1] of { mtype };
chan sys_user   = [1] of { mtype };
chan sys_locker = [1] of { mtype };
chan locker_sys = [1] of { mtype };
chan locker_user = [2] of { mtype };
proctype User() {
    user_sys!requestPickup;
    printf("User: requesting parcel pickup\n");
    sys_user?pickupAuthorized;
    printf("User: pickup authorized\n");
    locker_user?retrieveParcel;
    printf("User: parcel retrieved\n");
    locker_user?lockerClosed;
    printf("User: locker closed\n");
}
proctype LockerSystem() {
    user_sys?requestPickup;
    printf("System: pickup request received\n");
    sys_user!pickupAuthorized;
    printf("System: pickup authorized\n");
    sys_locker!unlockLocker;
    printf("System: locker unlock requested\n");
    locker_sys?lockerOpened;
    printf("System: locker opened\n");
}
proctype LockerUnit() {
    sys_locker?unlockLocker;
    printf("Locker: unlocking\n");
    locker_sys!lockerOpened;
    printf("Locker: opened\n");
    locker_user!retrieveParcel;
    printf("Locker: user may retrieve parcel\n");
    locker_user!lockerClosed;
    printf("Locker: closed\n");
}
init {
    atomic {
        run User();
        run LockerSystem();
        run LockerUnit();
    }
}
