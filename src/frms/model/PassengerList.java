package frms.model;

import frms.database.DataSource;

public class PassengerList {

    Passenger first;
    Passenger last;

    public PassengerList() {
        first = null;
        last = null;
    }


    public boolean isEmpty() {
        return (first == null);
    }


    public void insertPassenger(Passenger passenger) throws Exception {
        if (passenger == null)
            throw new NullPointerException("No Passengers Added.Null");
        if (first != null) {
            if (FindPassenger(passenger))
                throw new Exception("Mr/MRs " + passenger.getName() + " already exists!");
        }

        if (first == null) {
            first = passenger;
            last = passenger;
        }

        else {

            last.setNext(passenger);
            passenger.setPrevious(last);
            last=passenger;

        }

    }

    public void removePassenger(String flightCode, Passenger passenger) throws Exception {

        if (isEmpty() || passenger == null)
            throw new NullPointerException("There are no passengers to delete!");

        if (passenger == last && passenger == first) {

            first = null;
            last = null;
        } else if (passenger == first) {

            first.getNext().setPrevious(null);
            first = first.getNext();

        } else if (passenger == last) {
            last.getPrevious().setNext(null);
            last = last.getPrevious();
        } else {
            passenger.getPrevious().setNext(passenger.getNext());
            passenger.getNext().setPrevious(passenger.getPrevious());
        }

        DataSource.getInstance().deletePassenger(flightCode,passenger.getIdNo());

    }

    public Passenger getFirst() {
        return first;
    }

    public void setFirst(Passenger first) {
        this.first = first;
    }

    public Passenger getLast() {
        return last;
    }

    public void setLast(Passenger last) {
        this.last = last;
    }

    private boolean FindPassenger(Passenger passenger) {
            for (Passenger i = first; i != null; i = i.getNext()) {

                if (i.getPassportNo().equals(passenger.getPassportNo())) {
                    return true;
                }
            }
            return false;
        }

}

