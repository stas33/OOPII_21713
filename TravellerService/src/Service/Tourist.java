package Service;

// inheritance (Tourist class is child of Traveller class)
public class Tourist extends Traveller {

	public Tourist(String att1, int att2, double att3, double att4) {
		super(att1, att2, att3, att4);
		// TODO Auto-generated constructor stub
	}
	
	
	
	// Override method of Tourist class (same return type, same objects)
	// Having trouble on implementing this method
	@Override
	public int Similarity(Object obj1, Object obj2) {
		 
		 return 0;
	}
	
}
	
