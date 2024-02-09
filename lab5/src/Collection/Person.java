package Collection;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDateTime birthday; //Поле не может быть null
    private Double height; //Поле может быть null, Значение поля должно быть больше 0
    private int weight; //Значение поля должно быть больше 0
    private String passportID; //Длина строки должна быть не меньше 4, Длина строки не должна быть больше 49, Поле не может быть null

}
