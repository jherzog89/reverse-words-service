package test.java.com.herzog.words.reverse_words;

import com.herzog.words.reverse_words.model.ManipulatedString;
import com.herzog.words.reverse_words.repository.ManipulatedStringRepository;
import com.herzog.words.reverse_words.service.WordManipulationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// Java




class WordManipulationServiceTest {

    @Mock
    ManipulatedStringRepository repo;

    @InjectMocks
    WordManipulationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reverseWordsUtil_reversesWordsCorrectly() {
        assertThat(service.reverseWordsUtil("hello world")).isEqualTo("world hello");
        assertThat(service.reverseWordsUtil("  a b c  ")).isEqualTo("c b a");
        assertThat(service.reverseWordsUtil("single")).isEqualTo("single");
        assertThat(service.reverseWordsUtil("")).isEqualTo("");
        assertThat(service.reverseWordsUtil("   ")).isEqualTo("");
    }

    @Test
    void reverseString_setsFieldsAndSaves() {
        ManipulatedString input = new ManipulatedString();
        input.setOriginalString("foo bar");

        ManipulatedString saved = new ManipulatedString();
        saved.setOriginalString("foo bar");
        saved.setManipulatedString("bar foo");
        saved.setMicroserviceId("123@host");
        saved.setTimeCompleted(new Date());

        when(repo.save(any(ManipulatedString.class))).thenAnswer(invocation -> {
            ManipulatedString arg = invocation.getArgument(0);
            arg.setMicroserviceId("123@host");
            arg.setTimeCompleted(new Date());
            return arg;
        });

        ManipulatedString result = service.reverseString(input);

        assertThat(result.getOriginalString()).isEqualTo("foo bar");
        assertThat(result.getManipulatedString()).isEqualTo("bar foo");
        assertThat(result.getMicroserviceId()).isNotNull();
        assertThat(result.getTimeCompleted()).isNotNull();
        verify(repo, times(1)).save(any(ManipulatedString.class));
    }

    @Test
    void getLastTenReversedStrings_returnsList() {
        List<ManipulatedString> expected = Arrays.asList(new ManipulatedString(), new ManipulatedString());
        when(repo.findLast10ByTimeCompleted(any(Pageable.class))).thenReturn(expected);

        List<ManipulatedString> result = service.getLastTenReversedStrings();

        assertThat(result).isSameAs(expected);
        verify(repo, times(1)).findLast10ByTimeCompleted(PageRequest.of(0, 10));
    }

    @Test
    void deleteAllManipulatedStrings_callsRepoDeleteAll() {
        service.deleteAllManipulatedStrings();
        verify(repo, times(1)).deleteAll();
    }
}