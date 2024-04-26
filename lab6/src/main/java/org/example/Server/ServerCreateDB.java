package org.example.Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class ServerCreateDB {
    public static void createDB() throws IOException, SQLException {
        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));
        Connection db = getConnection(url, info);
        Statement st = db.createStatement();

        String createEnumFormEducation = "DO $$ \n" +
                "BEGIN\n" +
                "    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'form_education_enum__123') THEN\n" +
                "        CREATE TYPE form_education_enum__123 AS ENUM('DISTANCE_EDUCATION', 'FULL_TIME_EDUCATION', 'EVENING_CLASSES');\n" +
                "    END IF;\n" +
                "END $$;" +

                "DO $$ \n" +
                "BEGIN\n" +
                "    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'semester_enum__123') THEN\n" +
                "        CREATE TYPE semester_enum__123 AS ENUM('SECOND', 'FIFTH', 'SIXTH'); \n" +
                "    END IF;\n" +
                "END $$;" +
                "DO $$ \n" +
                "BEGIN\n" +
                "    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'hair_color_enum__123') THEN\n" +
                "        CREATE TYPE hair_color_enum__123 AS ENUM('BLACK', 'ORANGE', 'WHITE', 'BROWN');\n" +
                "    END IF;\n" +
                "END $$;" +
                "DO $$ \n" +
                "BEGIN\n" +
                "    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'eye_color_enum__123') THEN \n" +
                "        CREATE TYPE eye_color_enum__123 AS ENUM('BLUE', 'ORANGE', 'WHITE'); \n" +
                "    END IF;\n" +
                "END $$;" +

                "CREATE TABLE IF NOT EXISTS Coordinates__123( \n" +
                "id serial PRIMARY KEY, \n" +
                "x FLOAT NOT NULL, \n" +
                "y FLOAT NOT NULL \n " +
                "); \n" +

                "CREATE TABLE IF NOT EXISTS Users__123( \n" +
                "id serial PRIMARY KEY, \n" +
                "login varchar(255) NOT NULL UNIQUE, \n" +
                "password varchar(255) NOT NULL, \n" +
                "name varchar(255) NOT NULL, \n" +
                "birthday date NOT NULL, \n" +
                "hair_color hair_color_enum__123 NOT NULL, \n" +
                "eye_color eye_color_enum__123 NOT NULL \n" +
                ");" +


                "CREATE TABLE IF NOT EXISTS StudyGroup__123( \n" +
                "id serial PRIMARY KEY, \n" +
                "name_group varchar(255) NOT NULL UNIQUE, \n" +
                "id_coordinates integer REFERENCES Coordinates NOT NULL, \n" +
                "creation_date timestamp NOT NULL, \n" +
                "students_count integer NOT NULL, \n" +
                "average_mark FLOAT NOT NULL, \n" +
                "form_education form_education_enum__123 NOT NULL, \n" +
                "semester semester_enum__123 NOT NULL, \n" +
                "group_admin_id integer REFERENCES Users__123 NOT NULL UNIQUE);";

            st.execute(createEnumFormEducation);
            st.close();
            db.close();
    }
}
