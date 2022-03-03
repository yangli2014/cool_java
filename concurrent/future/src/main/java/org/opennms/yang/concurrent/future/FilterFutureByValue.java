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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class FilterFutureByValue {
    public static void main(String[] args) {
        CompletableFuture<String> f1 = CompletableFuture.completedFuture("one");
        CompletableFuture<String> f2 = new CompletableFuture<>();//CompletableFuture.completedFuture("two");
        CompletableFuture<String> f3 = CompletableFuture.completedFuture("three");
        CompletableFuture<String> f4 = CompletableFuture.completedFuture("four");


        CompletableFuture<String> future = f1.thenCompose(s-> CompletableFuture.supplyAsync( () -> s + " final"));
        System.out.println(future.join());

        System.out.println(f2.completeOnTimeout(null, 1, TimeUnit.SECONDS).join());
        System.out.println(f3.completeOnTimeout(null, 1, TimeUnit.SECONDS).join());
        System.out.println(f4.completeOnTimeout(null, 1, TimeUnit.SECONDS).join());

        /*List<CompletableFuture<String>> list = Arrays.asList(f1,f2, f3, f4);

        CompletableFuture<String> result = list.stream().filter(f -> f.join().equals("three")).findFirst().orElse(null);
        if(result.isDone()) {
            System.out.println(result.join());
            System.out.println(result.join());
        }*/
    }
}
