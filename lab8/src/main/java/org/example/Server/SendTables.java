package org.example.Server;

import org.example.Collections.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class SendTables {
    public static void sendTables(DatagramSocket serverSocket, DatagramPacket receivePacket) throws IOException, SQLException {
        InetAddress clientAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();
        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));

        Connection db = getConnection(url, info);

        String getNextIDStudyGroup = "select who_created_id, studygroup__123.id, name_group, x, y, creation_date, students_count, average_mark, form_education, semester, users__123.id, users__123.name, users__123.birthday, users__123.hair_color, users__123.eye_color from studygroup__123 join coordinates__123 on id_coordinates=coordinates__123.id join users__123 on users__123.id=group_admin_id;";
        Statement st = db.createStatement();
        List<Table> tableList = new ArrayList<>();
        ResultSet rs = st.executeQuery(getNextIDStudyGroup);
        int number = 0;
        while(rs.next()) {
            int whoCreatedId = rs.getInt("who_created_id");
            int id = rs.getInt("id");
            String groupName = rs.getString("name_group");
            Double x = rs.getDouble("x");
            Double y = rs.getDouble("y");
            java.time.LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
            int studentsCount = rs.getInt("students_count");
            Double averageMark = rs.getDouble("average_mark");
            FormOfEducation formOfEducation = FormOfEducation.valueOf(rs.getString("form_education"));
            Semester semester = Semester.valueOf(rs.getString("semester"));
            int adminId = rs.getInt(11);
            String adminName = rs.getString(12);
            java.time.LocalDate birthday = rs.getDate("birthday").toLocalDate();
            HairColor adminHairColor = HairColor.valueOf(rs.getString("hair_color"));
            EyeColor adminEyeColor = EyeColor.valueOf(rs.getString("eye_color"));
            Table table = new Table(whoCreatedId, id, groupName, x, y, creationDate, studentsCount, averageMark, formOfEducation, semester, adminId, adminName, birthday, adminHairColor, adminEyeColor);
            tableList.add(table);
            number+=1;
        }
        byte[] sendData = ByteBuffer.allocate(Integer.BYTES).putInt(number).array();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
        serverSocket.send(sendPacket);
        for (Table hui : tableList) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(hui);
            byte[] sendData1 = baos.toByteArray();
            sendPacket = new DatagramPacket(sendData1, sendData1.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);
            oos.close();
        }
    }
}
