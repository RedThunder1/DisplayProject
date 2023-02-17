package main.com.displayproject;

import javax.swing.*;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


public class Main {

    public static boolean running = true;
    public static String activeMode = "flashing";
    public static JFrame frame = new JFrame("Frame");
    protected static final Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.PINK, Color.MAGENTA, Color.YELLOW};

    public static void main(String[] args) {
        JButton startButton = new JButton("Start");
        startButton.setBorderPainted(false);
        startButton.setOpaque(true);
        startButton.setForeground(Color.GREEN);

        JButton stopButton = new JButton("stop");
        stopButton.setBorderPainted(false);
        stopButton.setOpaque(true);
        stopButton.setForeground(Color.RED);

        JButton mode = new JButton("Modes");
        mode.setBorderPainted(false);
        mode.setOpaque(true);
        mode.setForeground(Color.BLUE);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(startButton);
        frame.add(stopButton);
        frame.add(mode);
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 400);
        frame.setVisible(true);


        startButton.addActionListener(e -> running = true);
        stopButton.addActionListener(e -> {
            running = false;
            frame.getContentPane().setBackground(Color.WHITE);
        });
        mode.addActionListener(e -> {
            if (activeMode.equals("flashing")) {
                activeMode = "breathing";
                mode.setText("Breathing");
            } else if (activeMode.equals("breathing")) {
                activeMode = "smooth";
                mode.setText("Smooth Shifting");
            } else {
                activeMode = "flashing";
                mode.setText("Flashing");
            }
            modes(frame);
        });

    }

    public static void modes(JFrame frame) {
        if (running) {
            switch (activeMode) {
                case "flashing" -> {
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        int i = 0;
                        @Override
                        public void run() {
                            if (activeMode.equals("flashing")) {
                                if (Boolean.TRUE.equals(running)) {
                                    if (i == 5) {
                                        frame.getContentPane().setBackground(colors[i]);
                                        i = 0;
                                    } else {
                                        frame.getContentPane().setBackground(colors[i]);
                                        i++;
                                    }
                                }
                            } else {
                                cancel();
                            }
                        }
                    }, 0, 100L);
                }
                case "breathing" -> {
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        float i = 1;
                        final float increment = 0.005f;
                        boolean in = false;
                        @Override
                        public void run() {
                            if (activeMode.equals("breathing")) {
                                if (Boolean.TRUE.equals(running)) {
                                    if ((i - increment) < 0f) {
                                        in = true;
                                    } else if ((i + increment) > 1f) {
                                        in = false;
                                    }
                                    if(in) {
                                        i += increment;
                                        frame.getContentPane().setBackground(new Color(1, 0,0,i));
                                    } else {
                                        i -= increment;
                                        frame.getContentPane().setBackground(new Color(1, 0,0,i));
                                    }
                                }

                            } else {
                                cancel();
                            }
                        }
                    },0,50L);
                }
                case "smooth" -> {
                    //Smoothly shift between different colors
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {

                        float r = 0f;
                        final float rInc = 0.01f;
                        boolean rDown = false;
                        float g = 0f;
                        final float gInc = 0.02f;
                        boolean gDown = false;
                        float b = 0f;
                        final float bInc = 0.03f;
                        boolean bDown = false;
                        @Override
                        public void run() {
                            if (activeMode.equals("smooth")) {
                                if (Boolean.TRUE.equals(running)) {
                                    if ((r + rInc) > 1f) {
                                        rDown = true;
                                    } else if (r - rInc < 0f) {
                                        rDown = false;
                                    }

                                    if ((g + gInc) > 1f) {
                                        gDown = true;
                                    } else if (g - gInc < 0f) {
                                        gDown = false;
                                    }

                                    if ((b + bInc) > 1f) {
                                        bDown = true;
                                    } else if (b - bInc < 0f) {
                                        bDown = false;
                                    }

                                    if (rDown) {
                                        r -= rInc;
                                    } else {
                                        r += rInc;
                                    }

                                    if (gDown) {
                                        g -= gInc;
                                    } else {
                                        g += gInc;
                                    }

                                    if (bDown) {
                                        b -= bInc;
                                    } else {
                                        b += bInc;
                                    }
                                    frame.getContentPane().setBackground(new Color(r,g,b));
                                }
                            } else {
                                cancel();
                            }
                        }
                    }, 0, 30L);

                }
                default -> System.out.println("There was an error with the modes!");
            }
        } else {
            frame.getContentPane().setBackground(Color.WHITE);
        }



    }
}
