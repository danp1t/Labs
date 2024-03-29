package org.example.Managers;

/**
 * Класс для управления менеджерами
 */
public class StartManager {
    /**
     * Поле для хранения экземпляра collectionManager при запуске программы
     */
    private static CollectionManager collectionManager;
    /**
     * Поле для хранения экземпляра commandManager при запуске программы
     */
    private static CommandManager commandManager;

    /**
     * Getter для получения экземпляра collectionManager
     * @return CollectionManager пользователя
     */
    public static CollectionManager getCollectionManager(){
        return collectionManager;
    }

    /**
     * Устанавливает CollectionManager
     * @param collectionManager1
     */
    public static void setCollectionManager(CollectionManager collectionManager1){
        collectionManager = collectionManager1;
    }

    /**
     * Устанавливает значения поля CommandManager
     * @return CommandManager пользователя
     */
    public static CommandManager getCommandManager(){
        return commandManager;
    }

    /**
     * Устанавливает CommandManager
     * @param commandManager1
     */
    public static void setCommandManager(CommandManager commandManager1){
        commandManager = commandManager1;
    }
}
