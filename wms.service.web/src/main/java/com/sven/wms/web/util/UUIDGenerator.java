package com.sven.wms.web.util;

import java.util.UUID;

public class UUIDGenerator {

    public static String get() {
        return random().toString();
    }

    private static UUID random() {
        return UUID.randomUUID();
    }
}
