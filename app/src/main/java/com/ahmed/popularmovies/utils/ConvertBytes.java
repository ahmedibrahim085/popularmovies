package com.ahmed.popularmovies.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ConvertBytes {


    public static  byte[] convertToBytes(Object object) throws IOException {
            byte[] yourBytes = null;
        if (object !=null) {
            ObjectOutput out;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                out = new ObjectOutputStream(bos);
                out.writeObject(object);
                out.flush();
                yourBytes = bos.toByteArray();
            }
            // ignore close exception
            return yourBytes;
        }
        return yourBytes;
    }

    public static Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        Object yourBytes = null;
        if (bytes !=null) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                 ObjectInput in = new ObjectInputStream(bis)) {
                yourBytes = in.readObject();
                return yourBytes;
            }
        }
        return yourBytes;
    }
}
