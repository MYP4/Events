package data.mappers;

import data.entity.Ticket;
import data.models.TicketModel;

public class TicketToTicketModelMapper implements Mapper<Ticket, TicketModel> {

    @Override
    public TicketModel map(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        TicketModel ticketModel = new TicketModel();
        ticketModel.setUserId(ticket.getUserId());
        ticketModel.setSpecificId(ticket.getSpecificId());
        ticketModel.setStatus(ticket.getStatus());
        ticketModel.setUid(ticket.getUid());

        return ticketModel;
    }
}
