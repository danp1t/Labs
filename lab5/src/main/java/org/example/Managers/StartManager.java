package org.example.Managers;

public class StartManager {
    private static CollectionManager collectionManager;
    private static CommandManager commandManager;

    public static CollectionManager getCollectionManager(){
        return collectionManager;
    }

    public static void setCollectionManager(CollectionManager collectionManager1){
        collectionManager = collectionManager1;
    }
    public static CommandManager getCommandManager(){
        return commandManager;
    }

    public static void setCommandManager(CommandManager commandManager1){
        commandManager = commandManager1;
    }
}
