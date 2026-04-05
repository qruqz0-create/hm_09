import java.util.*;

//  FACADE

class TV {
    void on() { System.out.println("TV включен"); }
    void off() { System.out.println("TV выключен"); }
    void setChannel(String ch) { System.out.println("Канал: " + ch); }
}

class AudioSystem {
    void on() { System.out.println("Аудио включено"); }
    void off() { System.out.println("Аудио выключено"); }
    void setVolume(int v) { System.out.println("Громкость: " + v); }
}

class DVDPlayer {
    void play() { System.out.println("DVD play"); }
    void stop() { System.out.println("DVD stop"); }
}

class GameConsole {
    void on() { System.out.println("Консоль включена"); }
    void playGame() { System.out.println("Игра началась"); }
}

// Фасад
class HomeTheaterFacade {
    TV tv;
    AudioSystem audio;
    DVDPlayer dvd;
    GameConsole console;

    HomeTheaterFacade(TV tv, AudioSystem audio, DVDPlayer dvd, GameConsole console) {
        this.tv = tv;
        this.audio = audio;
        this.dvd = dvd;
        this.console = console;
    }

    void watchMovie() {
        System.out.println("\nСмотрим фильм:");
        tv.on();
        audio.on();
        audio.setVolume(10);
        dvd.play();
    }

    void playGame() {
        System.out.println("\nИграем:");
        tv.on();
        audio.on();
        console.on();
        console.playGame();
    }

    void offAll() {
        System.out.println("\nВыключение...");
        dvd.stop();
        audio.off();
        tv.off();
    }
}

//  COMPOSITE

interface FileSystemComponent {
    void display(String space);
    int getSize();
}

class File implements FileSystemComponent {
    String name;
    int size;

    File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public void display(String space) {
        System.out.println(space + "Файл: " + name + " (" + size + "KB)");
    }

    public int getSize() {
        return size;
    }
}

class Directory implements FileSystemComponent {
    String name;
    List<FileSystemComponent> list = new ArrayList<>();

    Directory(String name) {
        this.name = name;
    }

    void add(FileSystemComponent c) {
        if (!list.contains(c)) {
            list.add(c);
        }
    }

    void remove(FileSystemComponent c) {
        list.remove(c);
    }

    public void display(String space) {
        System.out.println(space + "Папка: " + name);
        for (FileSystemComponent c : list) {
            c.display(space + "  ");
        }
    }

    public int getSize() {
        int sum = 0;
        for (FileSystemComponent c : list) {
            sum += c.getSize();
        }
        return sum;
    }
}

//  MAIN

public class Lab9_DesignPatterns {
    public static void main(String[] args) {

        // Facade
        TV tv = new TV();
        AudioSystem audio = new AudioSystem();
        DVDPlayer dvd = new DVDPlayer();
        GameConsole console = new GameConsole();

        HomeTheaterFacade home = new HomeTheaterFacade(tv, audio, dvd, console);

        home.watchMovie();
        home.playGame();
        home.offAll();

        // Composite
        File f1 = new File("doc.txt", 10);
        File f2 = new File("img.jpg", 50);

        Directory folder = new Directory("Мои файлы");
        folder.add(f1);
        folder.add(f2);

        Directory root = new Directory("Главная");
        root.add(folder);

        System.out.println("\nФайловая система:");
        root.display("");

        System.out.println("Общий размер: " + root.getSize() + "KB");
    }
}