package frms.model;

public class FlightSLDS {

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

    private FlightSLDS() {
        head = null;
        tail = null;
    }

    public static FlightSLDS getInstance() {

        if (flightSLDS == null) {
            flightSLDS = new FlightSLDS();
        }

        return flightSLDS;
    }

    public void addFlight(Flight flight) throws Exception {
        
        if (flight == null)
            throw new NullPointerException("The flight is null!");
        
            if (head != null) {
                if (searchFlight(flight))
               throw new Exception("Flight " + flight.getFlightCode() + " already exists!");
        }

        if (head == null) {
            head = flight;
            tail = flight;
        } 

        else {
        
        tail.setNext(flight);
          flight.setPrevious(tail);
          tail=flight;
    
        }
    
    
    }

    private boolean searchFlight(Flight flightToSearch)
    {

        for (Flight i = head; i.getNext() != null; i = i.getNext()) {

            if (i.getFlightCode().equalsIgnoreCase(flightToSearch.getFlightCode()))
                return true;

        }
            
        return false;
    }

    public void delete(Flight flightTodelete) {
        
        if (isEmpty() || flightTodelete == null) {
            throw new NullPointerException("There are no flights to delete!");
        }
    
        if( flightTodelete == tail && flightTodelete == head) {
        
            head=null;
            tail=null;
        }

        else if(flightTodelete == head) {
            
            head.getNext().setPrevious(null);
            head=head.getNext();

        }

        else if ( flightTodelete == tail ) {
            tail.getPrevious().setNext(null);
            tail= tail.getPrevious();
        }

        else {
            flightTodelete.getPrevious().setNext(flightTodelete.getNext());
            flightTodelete.getNext().setPrevious(flightTodelete.getPrevious());
        }

}

    public void update (Flight oldFlight , Flight updatedFlight) throws Exception {

        if (oldFlight == null || updatedFlight == null)
            throw new NullPointerException("There are no flights to update!");

        if (oldFlight == head && oldFlight == tail) {
            head = updatedFlight;
            tail = updatedFlight;
        } else if (oldFlight == head) {
            head = updatedFlight;
            oldFlight.getNext().setPrevious(updatedFlight);
            head.setNext(oldFlight.getNext());
        } else if (oldFlight == tail) {
            tail = updatedFlight;
            tail.setNext(null);
            tail.setPrevious(oldFlight.getPrevious());
            oldFlight.getPrevious().setNext(tail);
        } else {

        oldFlight.getNext().setPrevious(updatedFlight);
        oldFlight.getPrevious().setNext(updatedFlight);

        }

        }
  
    public boolean isEmpty() {
        return head == null;
    }

    public Flight search(String origin,String destination) {

        Flight searchedFlights = null;

        for (Flight i = head; i != null; i = i.getNext()) {

            if (i.getOrigin().equalsIgnoreCase(origin) && i.getDestination().equalsIgnoreCase(destination)) {

                if (searchedFlights == null)
                    searchedFlights = i;

                else  {
                    searchedFlights.setNext(i);
                }

                break;
            }
        }

        return searchedFlights;


    }

}
