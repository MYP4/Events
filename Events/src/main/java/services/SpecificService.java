package services;

import data.mappers.SpecificModelToSpecificMapper;
import data.mappers.SpecificToSpecificModelMapper;
import data.repositories.EventRepository;
import data.repositories.SpecificRepository;

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
}
