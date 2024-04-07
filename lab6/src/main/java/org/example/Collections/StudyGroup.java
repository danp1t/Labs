package org.example.Collections;

/**
 * Данный класс описывает учебные группы
 */
public class StudyGroup implements Comparable<StudyGroup> {
    /**
     * Поле id группы
     * Значение данного поля должно быть больше 0
     * Значение данного поля должно быть уникальным
     * Значение этого поля генерируется автоматически
     */
    private int id;
    /**
     * Поле названия учебной группы
     * Значение поля не может быть null
     * Значение поля не может быть пустой строкой
     */
    private String name;
    /**
     * Поле координат учебной группы
     * Значение данного поля не может быть null
     */
    private Coordinates coordinates;
    /**
     * Поле даты создания учебной группы
     * Значение поля не может быть null
     * Значение этого поля генерируется автоматически
     */
    private java.time.LocalDateTime creationDate;
    /**
     * Поле количества студентов в учебной группе
     * Значения поля должно быть больше 0
     * Значение поля не может быть null
     */
    private Integer studentsCount;
    /**
     * Поле средней оценки учащихся в учебной группе
     * Значение поля должно быть больше 0
     * Значение поля не может быть null
     */
    private Double averageMark;
    /**
     * Поле формы обучения
     * Значение поля не может быть null
     */
    private FormOfEducation formOfEducation;
    /**
     * Поле семестра обучения
     * Значение поля не может быть null
     */
    private Semester semesterEnum;
    /**
     * Поле старосты группы
     * Значение поля не может быть null
     */
    private Person groupAdmin;

    /**
     * Конструктор данного класса
     * @param id id учебной группы
     * @param name название группы
     * @param coordinates координаты группы
     * @param creationDate дата создания группы
     * @param studentsCount количество студентов в группе
     * @param averageMark средняя оценка группы
     * @param formOfEducation форма обучения группы
     * @param semesterEnum семестр обучения группы
     * @param groupAdmin староста группы
     */
    public StudyGroup(int id, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, Integer studentsCount, Double averageMark, FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }
    public StudyGroup(){

    }

    /**
     * Getter поля id
     * @return возвращает id группы
     */
    public int getID(){
        return this.id;
    }

    /**
     * Getter поля name
     * @return возвращает название группы
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter поля coordinates
     * @return возвращает координаты группы
     */
    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    /**
     * Getter поля creationDate
     * @return возвращает поля создания группы
     */
    public java.time.LocalDateTime getCreationDate(){
        return this.creationDate;
    }

    /**
     * Getter поля studentsCount
     * @return возвращает количество студентов в учебной группе
     */
    public Integer getStudentsCount(){
        return this.studentsCount;
    }

    /**
     * Getter поля averageMark
     * @return возвращает среднюю оценку учеников группы
     */
    public Double getAverageMark(){
        return this.averageMark;
    }

    /**
     * Getter поля formOfEducation
     * @return возвращает форму обучения группы
     */
    public FormOfEducation getFormOfEducation(){
        return this.formOfEducation;
    }

    /**
     * Getter поля semesterEnum
     * @return возвращает семестр группы
     */
    public Semester getSemesterEnum(){
        return this.semesterEnum;
    }

    /**
     * Getter поля groupAdmin
     * @return возвращает старосту группы
     */
    public Person getGroupAdmin(){
        return this.groupAdmin;
    }

    /**
     * Метод текстового представление учебной группы
     * @return читабельное содержание учебной группы
     */
    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", averageMark=" + averageMark +
                ", formOfEducation=" + formOfEducation +
                ", semesterEnum=" + semesterEnum +
                ", groupAdmin=" + groupAdmin.toString() +
                '}';
    }

    /**
     * Метод для сравнения учебных групп
     * Сравнение происходит по количеству студентов в учебной группе
     * Если количество в двух группах одинаковое, то возвращается 1, для избежаний путаницы.
     * @param o the object to be compared.
     * @return возвращается значение, которое затем используется в сортировки
     */
    @Override
    public int compareTo(StudyGroup o) {
        if ((this.getStudentsCount() - o.getStudentsCount()) == 0) {
            return 1;
        }
        else if ((this.getStudentsCount() - o.getStudentsCount()) > 0) {
            return 1;
        }
        else {
            return -1;
        }
    }
}

