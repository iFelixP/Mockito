package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EchoServiceTestWithMocks {

    @Test
    public void givenValidRequestAndResponse_whenEcho_theTrueIsResponse() throws IOException {

        // Given:

        EchoService echoService = new EchoService();

        String request = "Hello World!";
        byte[] messageInBytes = new byte[] { 'H','e','l','l','o',' ','W','o','r','l','d','!' };

        OutputStream outputStream = mock(OutputStream.class);
        InputStream inputStream = mock(InputStream.class);

        when(inputStream.readAllBytes()).thenReturn(messageInBytes);

        // When:

        boolean response = echoService.sendEchoMessage(request, outputStream, inputStream);

        // Then:

        verify(inputStream).readAllBytes();
        verify(outputStream).write(messageInBytes);
        verifyNoMoreInteractions(inputStream,outputStream);

        assertTrue(response);
    }

    @Test
    public void givenValidRequestAndGrongResponse_whenEcho_theFalseIsResponse() throws IOException {

        // Given:

        EchoService echoService = new EchoService();

        String request = "Hello World!";
        byte[] messageResponse = new byte[] { 'H','e','l','l','o',' ','W','o','r','l','d' };
        byte[] messageRequest = new byte[] { 'H','e','l','l','o',' ','W','o','r','l','d','!' };

        OutputStream outputStream = mock(OutputStream.class);
        InputStream inputStream = mock(InputStream.class);

        when(inputStream.readAllBytes()).thenReturn(messageResponse);

        // When:

        boolean response = echoService.sendEchoMessage(request, outputStream, inputStream);

        // Then:

        verify(inputStream).readAllBytes();
        verify(outputStream).write(messageRequest);
        verifyNoMoreInteractions(inputStream,outputStream);

        assertFalse(response);
    }
}
