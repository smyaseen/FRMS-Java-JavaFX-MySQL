package frms.model;

import frms.database.DataSource;

public class PassengerList {

    //  == fields ==

    Passenger first;
    Passenger last;

    //  == constructor ==

    public PassengerList() {
        first = null;
        last = null;
    }

    //  == check for empty ==

    public boolean isEmpty() {
        return (first == null);
    }


    // adds passenger with complexity
    // O(1)
    // Omega(1)
    public void insertPassenger(Passenger passenger) throws Exception {

      // check first if null
       if (passenger == null)
            throw new NullPointerException("No Passengers Added.Null");

       // check for passenger if already available
       if (first != null) {
            if (FindPassenger(passenger))
                throw new Exception("Mr/MRs " + passenger.getName() + " already exists!");
        }

       // if head is empty, add in head
        if (first == null) {
            first = passenger;
            last = passenger;
        }

        // else add in tail
        else {

            last.setNext(passenger);
            passenger.setPrevious(last);
            last=passenger;

        }

    }

    // delete passenger with complexity
    // O(1)
    // Omega(1)
    public void removePassenger(String flightCode, Passenger passenger) throws Exception {

        // check if empty
        if (isEmpty() || passenger == null)
            throw new NullPointerException("There are no passengers to delete!");

        // if first is head and tail, means only one is available
        // replace
        if (passenger == last && passenger == first) {

            first = null;
            last = null;

            // if its head, set head to head's next
            // set head's previous now to null
        } else if (passenger == first) {

            first.getNext().setPrevious(null);
            first = first.getNext();


            // if last, set previous's next to null
            // set last to last's previous
        } else if (passenger == last) {
            last.getPrevious().setNext(null);
            last = last.getPrevious();
            // else its in between
            // so set the next's previous to current previous
            // and set previous's next to current next
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

    // check if passenger already available
    private boolean FindPassenger(Passenger passenger) {
            for (Passenger i = first; i != null; i = i.getNext()) {

                if (i.getPassportNo().equalsIgnoreCase(passenger.getPassportNo())
            || i.getIdNo().equalsIgnoreCase(passenger.getIdNo())) {
                    return true;
                }
            }
            return false;
        }

}

