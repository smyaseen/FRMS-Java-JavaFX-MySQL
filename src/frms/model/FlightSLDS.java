package frms.model;

public class FlightSLDS {

    // == fields ==

    private static FlightSLDS flightSLDS = new FlightSLDS();

    Flight head;
    Flight tail;


    public Flight getHead() {
        return head;
    }

    public void setHead(Flight head) {
        this.head = head;
    }

    public Flight getTail() {
        return tail;
    }

    public void setTail(Flight tail) {
        this.tail = tail;
    }

    // == constructor ==

    private FlightSLDS() {
        head = null;
        tail = null;
    }

    // == singleton method call ==

    public static FlightSLDS getInstance() {

        if (flightSLDS == null) {
            flightSLDS = new FlightSLDS();
        }

        return flightSLDS;
    }

    // adds flight with complexity
    // O(1)
    // Omega(1)
    public void addFlight(Flight flight) throws Exception {

        // check if obj not null
        if (flight == null)
            throw new NullPointerException("The flight is null!");

        // check if flight code already available
            if (head != null) {
                if (searchFlight(flight))
               throw new Exception("Flight " + flight.getFlightCode() + " already exists!");
        }

            // if head is empty , add in head
        if (head == null) {
            head = flight;
            tail = flight;
        } 

        // else add in tail
        else {
        
        tail.setNext(flight);
          flight.setPrevious(tail);
          tail=flight;
    
        }
    
    
    }

    // searches flight with flight code
    // complexity
    // O(n)
    // Omega(n)
    private boolean searchFlight(Flight flightToSearch)
    {

        for (Flight i = head; i.getNext() != null; i = i.getNext()) {

            if (i.getFlightCode().equalsIgnoreCase(flightToSearch.getFlightCode()))
                return true;

        }
            
        return false;
    }

    // delete flight with complexity
    // O(1)
    // Omega(1)
    public void delete(Flight flightTodelete) {

        // check if not empty or obj not null
        if (isEmpty() || flightTodelete == null) {
            throw new NullPointerException("There are no flights to delete!");
        }

        // check if the one to delete is same as head and tail
        // means only one avaialble
        // then set both to null
        if( flightTodelete == tail && flightTodelete == head) {
        
            head=null;
            tail=null;
        }

        // if its head, set the next one to head
        // set now head to  null
        else if(flightTodelete == head) {
            
            head.getNext().setPrevious(null);
            head=head.getNext();

        }

        // if its tail, set tail to previous
        else if ( flightTodelete == tail ) {
            tail.getPrevious().setNext(null);
            tail= tail.getPrevious();
        }

        // else definitely flight is in between
        // so set the next's previous to current previous
        // and set previous's next to current next
        else {
            flightTodelete.getPrevious().setNext(flightTodelete.getNext());
            flightTodelete.getNext().setPrevious(flightTodelete.getPrevious());
        }

}

    // update flight with complexity
    // O(1)
    // Omega(1)
    public void update (Flight oldFlight , Flight updatedFlight) throws Exception {

        // check if not null
        if (oldFlight == null || updatedFlight == null)
            throw new NullPointerException("There are no flights to update!");

        // if old is equal to head and tail, means only one available
        // just replace with both
        if (oldFlight == head && oldFlight == tail) {
            head = updatedFlight;
            tail = updatedFlight;

            // else if old is head
            // set old's next's previous to updated
            // set head to updated
            // set head to old's next

        } else if (oldFlight == head) {
            head = updatedFlight;
            oldFlight.getNext().setPrevious(updatedFlight);
            head.setNext(oldFlight.getNext());

            // if its tail, do the same thing just
            // in opposite of above one
        } else if (oldFlight == tail) {
            tail = updatedFlight;
            tail.setNext(null);
            tail.setPrevious(oldFlight.getPrevious());
            oldFlight.getPrevious().setNext(tail);

            // else obviously it's somewhere in between
            // set old's next's previous to updated
             // set old's previous's next to updated
        } else if (oldFlight.getNext() != null && oldFlight.getPrevious() != null){

        oldFlight.getNext().setPrevious(updatedFlight);
        oldFlight.getPrevious().setNext(updatedFlight);

        }

        }

  // check if empty
    public boolean isEmpty() {
        return head == null;
    }

    // search with supplied origin and destination
    // adds flight with complexity
    // O(n)
    // Omega(n)
    public Flight search(String origin,String destination) {

        // search for flight
        // make a link list of avaialbe flights on route
        // and return

        Flight searchedFlights = null;
        Flight currentFlight = null;

        for (Flight i = head; i != null; i = i.getNext()) {

            if (i.getOrigin().equalsIgnoreCase(origin.trim()) && i.getDestination().equalsIgnoreCase(destination.trim())) {
                if (searchedFlights == null) {
                    searchedFlights = i;
                    currentFlight = i;
                }

                else if (searchedFlights != null) {
                    currentFlight.setNext(i);
                    currentFlight = i;
                }

            }
        }

        return searchedFlights;


    }

}
