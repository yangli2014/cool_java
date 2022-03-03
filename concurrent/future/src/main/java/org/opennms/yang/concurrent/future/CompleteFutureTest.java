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

package org.opennms.yang.concurrent.future;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CompleteFutureTest {
    private static List<String> data = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future =  new CompletableFuture<>(); //CompletableFuture.completedFuture("Hello World");
        String data = null;
        display(future);
        display(future);
        future.complete("ha ha");

    }
    final static AtomicInteger sessionID = new AtomicInteger();
    private static void display(CompletableFuture<String> future){
        final int currentID = sessionID.incrementAndGet();
        future.whenComplete((s, e) -> {

            if(currentID != sessionID.get()) {
                return;
            }
            System.out.println(currentID);
           System.out.println(s);
        });
    }


}
