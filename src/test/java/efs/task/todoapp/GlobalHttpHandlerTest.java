package efs.task.todoapp;

import com.sun.net.httpserver.HttpExchange;
import efs.task.todoapp.handlers.GlobalHttpHandler;
import efs.task.todoapp.handlers.RestHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GlobalHttpHandlerTest {
    @Mock
    RestHandler taskRestHandlerMock;


    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void handlingPutRequestMethodShouldRedirectToRestPutHandler() throws IOException {
        //given
        GlobalHttpHandler globalHttpHandler = new GlobalHttpHandler(taskRestHandlerMock);

        HttpExchange exchangeMock = mock(HttpExchange.class);

        when(exchangeMock.getRequestMethod()).thenReturn("PUT");

        //when
        globalHttpHandler.handle(exchangeMock);

        //then
        verify(taskRestHandlerMock).handlePut();
    }

    @Test
    void handlingPostRequestMethodShouldRedirectToRestPutHandler() throws IOException {
        //given
        GlobalHttpHandler globalHttpHandler = new GlobalHttpHandler(taskRestHandlerMock);

        HttpExchange exchangeMock = mock(HttpExchange.class);

        when(exchangeMock.getRequestMethod()).thenReturn("POST");

        //when
        globalHttpHandler.handle(exchangeMock);

        //then
        verify(taskRestHandlerMock).handlePost();
    }

    @Test
    void handlingGetRequestMethodShouldRedirectToRestPutHandler() throws IOException {
        //given
        GlobalHttpHandler globalHttpHandler = new GlobalHttpHandler(taskRestHandlerMock);

        HttpExchange exchangeMock = mock(HttpExchange.class);

        when(exchangeMock.getRequestMethod()).thenReturn("GET");

        //when
        globalHttpHandler.handle(exchangeMock);

        //then
        verify(taskRestHandlerMock).handleGet();
    }

    @Test
    void handlingDeleteRequestMethodShouldRedirectToRestPutHandler() throws IOException {
        //given
        GlobalHttpHandler globalHttpHandler = new GlobalHttpHandler(taskRestHandlerMock);

        HttpExchange exchangeMock = mock(HttpExchange.class);

        when(exchangeMock.getRequestMethod()).thenReturn("DELETE");

        //when
        globalHttpHandler.handle(exchangeMock);

        //then
        verify(taskRestHandlerMock).handleDelete();
    }
}
