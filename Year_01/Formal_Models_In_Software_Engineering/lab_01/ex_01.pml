mtype = {
    enrollStudent,
    isStudentEligible,
    getSeminarHistory,
    seminarHistory,
    eligibilityStatus,
    enrollmentStatus
}

#define iSize 100

chan StudentSeminar  = [iSize] of { mtype };
chan SeminarCourse   = [iSize] of { mtype };
chan StudentCourse   = [iSize] of { mtype };

proctype Student(){
Start:
    StudentSeminar!enrollStudent;
    goto Next1;
Next1:
    StudentCourse?getSeminarHistory;
    goto Next2;
Next2:
    StudentCourse!seminarHistory;
    goto Next3;
Next3:
    StudentSeminar??enrollmentStatus;
}

proctype Seminar()
{
Start: atomic{
    StudentSeminar??enrollStudent
    goto Next1;
}

Next1: atomic{
    SeminarCourse!isStudentEligible;
}

}

init {
    run Student();
    run Seminar();
}