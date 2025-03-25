package com.nazran.springboot3firebseauth.utils;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

/**
 * Utility class for handling image-related operations, including conversion
 * of images to byte arrays and encoding them to Base64 format.
 */
@Component
public class ImageUtils {

    private static final Tika TIKA = new Tika();

    @Value("${BASE64_PREFIX}")
    private String base64Prefix;

    /**
     * Converts the provided image file to a byte array.
     *
     * @param file the {@link MultipartFile} representing the image.
     * @return a byte array representing the image's content.
     * @throws IOException if an error occurs while reading the file's content.
     */
    public byte[] convertToByteArray(MultipartFile file) throws IOException {
        return file.getBytes(); // Directly returning file bytes
    }

    /**
     * Encodes the provided byte array into a Base64-encoded string.
     * The resulting string is prefixed with the MIME type of the file and the prefix specified in {@code BASE64_PREFIX}.
     *
     * @param file the byte array representing the file's content.
     * @return a Base64-encoded string including MIME type and prefix, or {@code null} if the input byte array is {@code null}.
     */
    public String encodeToBase64(byte[] file) {
        if (file == null) {
            return null;
        }
        // Detect the MIME type of the file
        String mimeType = TIKA.detect(file);

        // Encode the byte array to Base64
        String base64Icon = Base64.getEncoder().encodeToString(file);

        // Return the Base64-encoded string with MIME type and prefix
        return "data:" + mimeType + ";" + base64Prefix + "," + base64Icon;
    }
}