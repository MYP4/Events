package services;

import data.entity.Event;
import data.entity.Specific;
import data.exceptions.CreationException;
import data.exceptions.DBException;
import data.mappers.SpecificModelToSpecificMapper;
import data.mappers.SpecificToSpecificModelMapper;
import data.models.SpecificModel;
import data.repositories.EventRepository;
import data.repositories.SpecificRepository;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

public class SpecificService {
    private final EventRepository eventRepository;
    private final SpecificRepository specificRepository;
    private final SpecificModelToSpecificMapper specificModelToSpecificMapper;
    private final SpecificToSpecificModelMapper specificToSpecificModelMapper;
    private static final Logger logger = Logger.getLogger(SpecificService.class);

    public SpecificService(EventRepository eventRepository,
                           SpecificRepository specificRepository,
                           SpecificModelToSpecificMapper specificModelToSpecificMapper,
                           SpecificToSpecificModelMapper specificToSpecificModelMapper) {
        this.eventRepository = eventRepository;
        this.specificRepository = specificRepository;
        this.specificModelToSpecificMapper = specificModelToSpecificMapper;
        this.specificToSpecificModelMapper = specificToSpecificModelMapper;
    }

    public List<SpecificModel> getAll() throws DBException {
        try {
            List<Specific> specifics = specificRepository.getAll();
            return specifics.stream().map(specificToSpecificModelMapper::map).toList();
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public SpecificModel getById(UUID id) throws DBException {
        try {
            return specificToSpecificModelMapper.map(specificRepository.getById(id));
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public SpecificModel create(SpecificModel specificModel) throws DBException {
        try {
            Event event = eventRepository.getById(specificModel.getEventId());
            if (event == null) {
                logger.error("Failed to create specific: event not exists");
                throw new DBException("Failed to create specific: event not exists");
            }
            Specific specific = specificModelToSpecificMapper.map(specificModel);
            specific.setUid(UUID.randomUUID());
            specific = specificRepository.create(specific);
            return specificToSpecificModelMapper.map(specific);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBException("Failed to create specific: " + e.getMessage());
        }
    }

    public void update(SpecificModel specificModel) throws DBException {
        try {
            specificRepository.update(specificModelToSpecificMapper.map(specificModel));
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public void delete(UUID id) throws DBException {
        try {
            specificRepository.delete(id);
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }
}
