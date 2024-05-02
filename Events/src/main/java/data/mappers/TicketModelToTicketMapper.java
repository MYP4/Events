package data.mappers;

import data.entity.Ticket;
import data.models.TicketModel;

public class TicketModelToTicketMapper implements Mapper<TicketModel, Ticket> {

    @Override
    public Ticket map(TicketModel ticketModel) {
        if (ticketModel == null) {
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setUserId(ticketModel.getUserId());
        ticket.setSpecificId(ticketModel.getSpecificId());
        ticket.setStatus(ticketModel.getStatus());
        ticket.setUid(ticketModel.getUid());

        return ticket;
    }
}
