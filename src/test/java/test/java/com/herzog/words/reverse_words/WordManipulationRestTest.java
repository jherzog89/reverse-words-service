package test.java.com.herzog.words.reverse_words;
import com.herzog.words.reverse_words.controller.WordManipulationRest;
import com.herzog.words.reverse_words.model.ManipulatedString;
import com.herzog.words.reverse_words.service.WordManipulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WordManipulationRestTest {

    private WordManipulationService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        service = mock(WordManipulationService.class);
        WordManipulationRest controller = new WordManipulationRest();
        // Inject mock service
        controller.setSrv(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testReverseString_Post() throws Exception {
        ManipulatedString input = new ManipulatedString();
        input.setOriginalString("hello world");
        ManipulatedString reversed = new ManipulatedString();
        reversed.setOriginalString("hello world");
        reversed.setManipulatedString("world hello");
        List<ManipulatedString> lastTen = Arrays.asList(reversed);

        when(service.reverseString(any(ManipulatedString.class))).thenReturn(reversed);
        when(service.getLastTenReversedStrings()).thenReturn(lastTen);

        mockMvc.perform(post("/reverseWords")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"originalString\":\"hello world\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].manipulatedString", is("world hello")));

        // Verify apiUsed is set
        ArgumentCaptor<ManipulatedString> captor = ArgumentCaptor.forClass(ManipulatedString.class);
        verify(service).reverseString(captor.capture());
        ManipulatedString passed = captor.getValue();
        // The apiUsed field should be set to something (not null)
        assert passed.getApiUsed() != null;
    }

    @Test
    void testGetLastTenReversedStrings_Get() throws Exception {
        ManipulatedString ms = new ManipulatedString();
        ms.setManipulatedString("foo bar");
        List<ManipulatedString> lastTen = Collections.singletonList(ms);

        when(service.getLastTenReversedStrings()).thenReturn(lastTen);

        mockMvc.perform(get("/reverseWords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].manipulatedString", is("foo bar")));
    }

    @Test
    void testDeleteAllManipulatedStrings_Delete() throws Exception {
        ManipulatedString ms = new ManipulatedString();
        ms.setManipulatedString("baz qux");
        List<ManipulatedString> lastTen = Collections.singletonList(ms);

        doNothing().when(service).deleteAllManipulatedStrings();
        when(service.getLastTenReversedStrings()).thenReturn(lastTen);

        mockMvc.perform(delete("/reverseWords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].manipulatedString", is("baz qux")));

        verify(service, times(1)).deleteAllManipulatedStrings();
        verify(service, times(1)).getLastTenReversedStrings();
    }
}