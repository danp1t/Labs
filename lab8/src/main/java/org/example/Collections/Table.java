package org.example.Collections;

import java.io.Serial;
import java.io.Serializable;

public class Table implements Serializable {
    @Serial
    private static final long serialVersionUID = 153;
    private String whoCreatedId;
    private int id;
    private String groupName;
    private Double x;
    private Double y;
    private java.time.LocalDateTime creationDate;
    private int studentsCount;
    private Double averageMark;
    private FormOfEducation formOfEducation;
    private Semester semester;
    private int adminId;
    private String adminName;
    private java.time.LocalDate birthday;
    private HairColor adminHairColor;
    private EyeColor adminEyeColor;

    public Table(String whoCreatedId, int id, String groupName, Double x, Double y, java.time.LocalDateTime creationDate,
                 int studentsCount, Double averageMark, FormOfEducation formOfEducation, Semester semester,
                 int adminId, String adminName, java.time.LocalDate birthday, HairColor adminHairColor, EyeColor adminEyeColor) {
        this.whoCreatedId = whoCreatedId;
        this.id = id;
        this.groupName = groupName;
        this.x = x;
        this.y = y;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.semester = semester;
        this.adminId = adminId;
        this.adminName = adminName;
        this.birthday = birthday;
        this.adminHairColor = adminHairColor;
        this.adminEyeColor = adminEyeColor;
    }

    public String getWhoCreatedId() {
        return whoCreatedId;
    }
    public int getId() {
        return id;
    }
    public String getGroupName() {
        return groupName;
    }
    public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }
    public int getStudentsCount() {
        return studentsCount;
    }
    public Double getAverageMark() {
        return averageMark;
    }
    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }
    public Semester getSemester() {
        return semester;
    }
    public int getAdminId() {
        return adminId;
    }
    public String getAdminName() {
        return adminName;
    }
    public java.time.LocalDate getBirthday() {
        return birthday;
    }
    public HairColor getAdminHairColor() {
        return adminHairColor;
    }
    public EyeColor getAdminEyeColor() {
        return adminEyeColor;
    }
    public void setWhoCreatedId(String whoCreatedId) {
        this.whoCreatedId = whoCreatedId;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public void setY(Double y) {
        this.y = y;
    }
    public void setCreationDate(java.time.LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }
    public void setAverageMark(Double averageMark) {
        this.averageMark = averageMark;
    }
    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }
    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public void setBirthday(java.time.LocalDate birthday) {
        this.birthday = birthday;
    }
    public void setAdminHairColor(HairColor adminHairColor) {
        this.adminHairColor = adminHairColor;
    }
    public void setAdminEyeColor(EyeColor adminEyeColor) {
        this.adminEyeColor = adminEyeColor;
    }

}
