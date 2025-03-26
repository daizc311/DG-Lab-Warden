package com.wastingmisaka.dg_lab_warden.Threads;

import com.wastingmisaka.dg_lab_warden.messageUtils.MessageSender;

import java.io.IOException;

import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.current_pulse;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.*;

public class pulse_send extends Thread {
    boolean green = true;
    MessageSender messageSender = new MessageSender();

    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                flag = false;
            }
            if (progress_session == null || current_pulse == null) {
                continue;
            }
            try {
                switch (current_pulse) {
                    case "empty" -> {
                        messageSender.message_entry("clear", 2, "0", 0);
                        messageSender.message_entry("clear", 1, "0", 0);
                    }
                    case "hso" -> {
                        if (a_enabled) {
                            messageSender.message_entry("pulse", 1, current_pulse, 0);
                            green = false;
                        }
                        if (b_enabled) {
                            messageSender.message_entry("pulse", 2, current_pulse, 1);
                            green = false;
                        }
                    }
                    default -> {
                        messageSender.message_entry("clear", 1, "0", 0);
                        messageSender.message_entry("clear", 2, "0", 0);
                        current_pulse = "hso";
                    }

                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
