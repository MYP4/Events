package services;

import data.entity.Event;
import data.entity.Specific;
import data.exceptions.CreationException;
import data.mappers.SpecificModelToSpecificMapper;
import data.mappers.SpecificToSpecificModelMapper;
import data.models.SpecificModel;
import data.repositories.EventRepository;
import data.repositories.SpecificRepository;

import java.util.List;
import java.util.UUID;

public class SpecificService {
    private final EventRepository eventRepository;
    private final SpecificRepository specificRepository;
    private final SpecificModelToSpecificMapper specificModelToSpecificMapper;
    private final SpecificToSpecificModelMapper specificToSpecificModelMapper;

    public SpecificService(EventRepository eventRepository,
                           SpecificRepository specificRepository,
                           SpecificModelToSpecificMapper specificModelToSpecificMapper,
                           SpecificToSpecificModelMapper specificToSpecificModelMapper) {
        this.eventRepository = eventRepository;
        this.specificRepository = specificRepository;
        this.specificModelToSpecificMapper = specificModelToSpecificMapper;
        this.specificToSpecificModelMapper = specificToSpecificModelMapper;
    }

    public List<SpecificModel> getAll() {
        List<Specific> specifics = specificRepository.getAll();
        return specifics.stream().map(specificToSpecificModelMapper::map).toList();
    }

    public SpecificModel getById(UUID id) {
        return specificToSpecificModelMapper.map(specificRepository.getById(id));
    }

    public SpecificModel create(SpecificModel specificModel) throws CreationException {
        try {
            Event event = eventRepository.getById(specificModel.getEventId());
            if (event == null)
                throw new CreationException("Failed to create specific: event not exists");

            Specific specific = specificModelToSpecificMapper.map(specificModel);
            return  specificToSpecificModelMapper.map(specificRepository.create(specific));

        } catch (Exception e) {
            throw new CreationException("Failed to create specific: " + e.getMessage());
        }
    }

    public void update(SpecificModel specificModel) {
        specificRepository.update(specificModelToSpecificMapper.map(specificModel));
    }

    public void delete(UUID id) {
        specificRepository.delete(id);
    }
}
