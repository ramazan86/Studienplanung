package data;

import java.io.Serializable;

/**
 * Created by Ramazan Cinardere on 03.09.15.
 */
public class Student implements Serializable{

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private String name = null;

    private String matrikelNumber;

    private boolean showAvatar = false,
                    sendEmail  = true;

    private String semester     = null;

    private String studySubject = null;

    private String email = null;

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////

    public Student(){

    }



    ////////////////////////////
    //         Methods        //
    ////////////////////////////


    /**
     *
     * Setter-/getter
     *
     */



    public String getMatrikelNumber() {

        if(matrikelNumber == null) {
            matrikelNumber = "NaN";
        }

        return matrikelNumber;
    }

    public void setMatrikelNumber(String matrikelNumber) {
        this.matrikelNumber = matrikelNumber;
    }

    public String getName() {
        if(name == null) {
            name = "NaN";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowAvatar() {
        return showAvatar;
    }

    public void setShowAvatar(boolean showAvatar) {
        this.showAvatar = showAvatar;
    }

    public String getSemester() {

        if(semester == null) {
            semester = "NaN";
        }

        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStudySubject() {

        if(studySubject == null) {
            studySubject = "NaN";
        }

        return studySubject;
    }

    public void setStudySubject(String studySubject) {
        this.studySubject = studySubject;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getEmail() {
        if(email == null) {
            email = "NaN";
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
