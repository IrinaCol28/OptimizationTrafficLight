package org.example;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class TrafficLight extends JPanel {
    private int id;
    private int queueLength;
    private String state;
    private Queue<Event> eventQueue;
    private Timer timer;

    public TrafficLight(int id) {
        this.id = id;
        this.queueLength = 0;
        this.state = "red";
        this.eventQueue = new LinkedList<>();
        this.timer = new Timer();
        setPreferredSize(new java.awt.Dimension(100, 100));
    }

    public void updateQueueLength(int length) {
        this.queueLength = length;
        System.out.println("Traffic Light " + id + " updated queue length to " + length);
        repaint();
    }

    public void receiveEvent(Event event) {
        this.eventQueue.add(event);
    }

    public void processEvent(Event event) {
        switch (event.getType()) {
            case "update_queue":
                updateQueueLength(event.getLength());
                break;
            case "change_light":
                changeLight(event.getState());
                break;
        }
    }

    public void changeLight(String state) {
        this.state = state;
        System.out.println("Traffic Light " + id + " changed light to " + state);
        repaint();
        if (state.equals("green")) {
            scheduleChangeLight("red", calculateGreenTime());
        } else if (state.equals("red")) {
            scheduleChangeLight("green", calculateRedTime());
        }
    }

    private void scheduleChangeLight(String state, long delay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendEvent(new Event("change_light", state, 0, id));
            }
        }, delay);
    }

    private long calculateGreenTime() {
        int baseTime = 5000; // базовое время в миллисекундах
        return baseTime + queueLength * 500;
    }

    private long calculateRedTime() {
        return 10000; // фиксированное время красного света
    }

    public void sendEvent(Event event) {
        for (TrafficLight light : Main.trafficLights) {
            if (light.getId() != event.getSenderId()) {
                light.receiveEvent(event);
            }
        }
    }

    public int getId() {
        return id;
    }

    public void run() {
        while (true) {
            if (!eventQueue.isEmpty()) {
                Event event = eventQueue.poll();
                processEvent(event);
            }
            try {
                Thread.sleep(100); // короткая задержка, чтобы избежать слишком быстрой обработки
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(state.equals("green") ? Color.GREEN : Color.RED);
        g.fillOval(10, 10, 80, 80);
        g.setColor(Color.BLACK);
        g.drawString("Queue: " + queueLength, 10, 95);
    }
}