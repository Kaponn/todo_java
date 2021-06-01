package efs.task.todoapp.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

public class Base64Utils {

  public static String encode(final String value) {
    final byte[] valueBytes = value.getBytes(StandardCharsets.ISO_8859_1);

    final Encoder base64Encoder = Base64.getEncoder();

    return base64Encoder.encodeToString(valueBytes);
  }

  public static String decode(final String valueInBase64) {
    final Decoder base64Decoder = Base64.getDecoder();

    final byte[] decodedValueBytes = base64Decoder.decode(valueInBase64);

    return new String(decodedValueBytes, StandardCharsets.ISO_8859_1);
  }
}
