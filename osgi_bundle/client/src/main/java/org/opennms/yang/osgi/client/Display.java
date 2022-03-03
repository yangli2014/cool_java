/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2021 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2021 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.yang.osgi.client;

import java.util.concurrent.TimeUnit;

import org.opennms.yang.osgi.common.Booking;

public class Display {

    private ClientService clientService;

    private int interval;

    private BookingDisplayThread thread;
    private boolean bookingThreadStarted = false;

    /**
     * This setter is used by Blueprint to inject the client service.
     */
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * Init method used to start the thread.
     */
    public void init() {
        thread = new BookingDisplayThread(clientService);
        thread.start();
    }

    /**
     * Destroy method used to stop the thread.
     */
    public void destroy() {
        thread.terminate();
    }

    private class BookingDisplayThread extends Thread {

        private ClientService clientService;
        private volatile boolean running = true;

        public BookingDisplayThread(ClientService clientService) {
            this.clientService = clientService;
        }

        @Override
        public void run() {
            while (running) {
                try {

                    // TODO test
                    Booking booking = new Booking("John Doo", "AF3030");
                    clientService.addBooking(booking);

                    System.out.println(displayBookings());
                    System.out.println("sleep for " + interval +" seconds");
                    TimeUnit.SECONDS.sleep(interval);
                } catch (Exception e) {
                    // nothing to do
                }
            }
        }

        private String displayBookings() {
            StringBuilder builder = new StringBuilder();
            for (Booking booking : clientService.bookings()) {
                builder.append(booking.getId()).append(" | ").append(booking.getCustomer()).append(" | ").append(booking.getFlight()).append("\n");
            }
            return builder.toString();
        }

        public void terminate() {
            running = false;
        }

    }
}
