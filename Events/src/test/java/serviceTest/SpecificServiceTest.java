package serviceTest;

import data.entity.Event;
import data.entity.Specific;
import data.exceptions.DBException;
import data.mappers.SpecificModelToSpecificMapper;
import data.mappers.SpecificToSpecificModelMapper;
import data.models.SpecificModel;
import data.repositories.EventRepository;
import data.repositories.SpecificRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.SpecificService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SpecificServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SpecificRepository specificRepository;

    @Mock
    private SpecificModelToSpecificMapper specificModelToSpecificMapper;

    @Mock
    private SpecificToSpecificModelMapper specificToSpecificModelMapper;

    @InjectMocks
    private SpecificService specificService;

    @Test
    void testGetAll() throws DBException {
        // Setup
        List<Specific> specifics = List.of(new Specific(), new Specific());
        when(specificRepository.getAll()).thenReturn(specifics);
        when(specificToSpecificModelMapper.map(any(Specific.class))).thenReturn(new SpecificModel());

        // Execute
        List<SpecificModel> result = specificService.getAll();

        // Verify
        verify(specificRepository, times(1)).getAll();
        verify(specificToSpecificModelMapper, times(specifics.size())).map(any(Specific.class));
        assertEquals(specifics.size(), result.size());
    }

    @Test
    void testGetById() throws DBException {
        // Setup
        UUID id = UUID.randomUUID();
        Specific specific = new Specific();
        specific.setUid(id);
        when(specificRepository.getById(id)).thenReturn(specific);
        when(specificToSpecificModelMapper.map(specific)).thenReturn(new SpecificModel());

        // Execute
        SpecificModel result = specificService.getById(id);

        // Verify
        verify(specificRepository, times(1)).getById(id);
        verify(specificToSpecificModelMapper, times(1)).map(specific);
        assertNotNull(result);
    }

    @Test
    void testDelete() throws DBException {
        // Setup
        UUID id = UUID.randomUUID();
        when(specificRepository.delete(id)).thenReturn(true);

        // Execute
        specificService.delete(id);

        // Verify
        verify(specificRepository, times(1)).delete(id);
    }
}
