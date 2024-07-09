package org.example;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Main {
    public static List<TrafficLight> trafficLights = new ArrayList<>();

    public static void main(String[] args) {
        // Создание графического интерфейса
        JFrame frame = new JFrame("Traffic Light Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(2, 2));

        // Создание светофоров
        for (int i = 0; i < 4; i++) {
            TrafficLight light = new TrafficLight(i);
            trafficLights.add(light);
            frame.add(light);
            new Thread(light::run).start();
        }

        // Пример инициализации и отправки начальных событий
        trafficLights.get(0).sendEvent(new Event("update_queue", null, 10, 0));
        trafficLights.get(1).sendEvent(new Event("update_queue", null, 5, 1));
        trafficLights.get(0).sendEvent(new Event("change_light", "green", 0, 0));
        trafficLights.get(1).sendEvent(new Event("change_light", "red", 0, 1));

        frame.setVisible(true);
    }
}