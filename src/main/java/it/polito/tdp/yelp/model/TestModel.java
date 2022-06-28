package it.polito.tdp.yelp.model;


public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model m = new Model() ;
				
		m.creaGrafo("Avondale","kM7mfXS8LgBPjRrqGb6i6g");
		System.out.println("vertici :"+m.nVertici()+"\n");
		System.out.println("archi :"+m.nEdge()+"\n");
		System.out.println(m.getArchiUscenti());
		
	}

}
