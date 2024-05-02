package data.mappers;

import data.entity.Specific;
import data.models.SpecificModel;

public class SpecificModelToSpecificMapper implements Mapper<SpecificModel, Specific> {

    @Override
    public Specific map(SpecificModel specificModel) {
        if (specificModel == null) {
            return null;
        }

        Specific specific = new Specific();
        specific.setEventId(specificModel.getEventId());
        specific.setDescription(specificModel.getDescription());
        specific.setTicketCount(specificModel.getTicketCount());
        specific.setPrice(specificModel.getPrice());
        specific.setAddress(specificModel.getAddress());
        specific.setUid(specificModel.getUid());

        return specific;
    }
}
