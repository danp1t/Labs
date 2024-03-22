package org.example.Managers;

import org.example.Collections.StudyGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static org.example.Managers.CollectionManager.getHashSet;
import static org.example.Managers.CollectionManager.getStudyGroups;

public class ElementManager {
    private StudyGroup element;

    public StudyGroup getElement(){
        return element;
    }

    public void setElement(StudyGroup element){
        this.element = element;
    }

    public void createElement(){
        int ID = nextID();
        System.out.println(nextID());
    }

    /**
     * Метод нахождения уникального ID
     * @return уникальный ID
     */
    private static int nextID(){
        //Получение текущей коллекции
        HashSet<StudyGroup> studyGroups = getStudyGroups();
        ArrayList<Integer> groups = new ArrayList<>();
        //Положить все ID из коллекции в массив
        for (StudyGroup group : studyGroups){
            groups.add(group.getID());
        }
        //Отсортировать массив
        Collections.sort(groups);
        int nextID = 1;
        for (Integer number : groups) {
            if (number == nextID) {
                nextID = nextID + 1;
            }
        }
        return nextID;
    }
}
