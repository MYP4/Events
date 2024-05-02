package data.mappers;

import data.entity.Specific;
import data.models.SpecificModel;

public class SpecificToSpecificModelMapper implements Mapper<Specific, SpecificModel> {

    @Override
    public SpecificModel map(Specific specific) {
        if (specific == null) {
            return null;
        }

        SpecificModel specificModel = new SpecificModel();
        specificModel.setEventId(specific.getEventId());
        specificModel.setDescription(specific.getDescription());
        specificModel.setTicketCount(specific.getTicketCount());
        specificModel.setPrice(specific.getPrice());
        specificModel.setAddress(specific.getAddress());
        specificModel.setUid(specific.getUid());

        return specificModel;
    }
}
