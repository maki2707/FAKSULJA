//Opisnik prostora stanja sadrzava informacije o pocetnom stanju, ciljnom stanju (ili
//stanjima) te prijelazima. Prva linija datoteke koja nije komentar sadrzi pocetno stanje,
//dok se u drugoj liniji nalaze ciljna stanja odvojena s jednim razmakom. Preostale linije
//opisnika prostora stanja sastoje se od zapisa funkcije prijelaza. Svaki redak je u sljedecem
//formatu:
//state: next_state_1,cost next_state_2,cost


import java.util.*;

public class MyNodes
{
    private String initialState;
    private List<String> goalStates; //jer moze biti vise ciljeva
    private HashMap<String, HashMap<String, Double>> neighbours;

    public MyNodes ()
    {

    }

    public MyNodes(String initialState, List<String> goalStates, HashMap<String, HashMap<String, Double>> neighbours)
    {
        this.initialState = initialState;
        this.goalStates = goalStates;
        this.neighbours = neighbours;
    }

    //generirane metode

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public List<String> getGoalStates() {
        return goalStates;
    }

    public void setGoalStates(List<String> goalStates) {
        this.goalStates = goalStates;
    }

    public HashMap<String, HashMap<String, Double>> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(HashMap<String, HashMap<String, Double>> neighbours) {
        this.neighbours = neighbours;
    }
}
