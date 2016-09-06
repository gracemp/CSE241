import java.util.Arrays;

//
// STRINGTABLE.JAVA
// A hash table mapping Strings to their positions in the the pattern sequence
// You get to fill in the methods for this part.
//
public class StringTable {
	Record[] stringArray;
	int currentSize;
	int count;

	public StringTable(int maxSize) {
		stringArray = new Record[2];
	}



	//
	// Insert a Record r into the table.  Return true if
	// successful, false if the table is full.  You shouldn't ever
	// get two insertions with the same key value, but you may
	// simply return false if this happens.
	//
	public boolean insert(Record r) {
		//check to see if load factor has exceeded 0.25
		if (((double)count+1)/stringArray.length<=0.25){
			int i=0;
			int slot= baseHash(r.key)%stringArray.length;
			int step = stepHash(r.key)%stringArray.length;
			while (i<stringArray.length){
				slot= (slot + step)%stringArray.length;
				//insert record if slot is empty
				if (stringArray[slot]==null){
					stringArray[slot]=r;
					r.toHashValue=toHashKey(r.key);
					count++;
					return true;
				}
				else{
					i++;
				}
			}
			return false;
		}
		//double the size if load factor is >1/4
		else{
			doubleSize();
			return insert(r);

		}
	}
	//
	// Delete a Record r from the table.  Note that you'll have to
	// find the record first unless you keep some extra information
	// in the Record structure.
	//
	public void remove(Record r) 
	{
		Record temp= find(r.key);
		//insert a string as a deleted placeholder
		temp.key="empty";
	}

	//
	// Find a record with a key matching the input.  Return the
	// record if it exists, or null if no matching record is found.
	//
	public Record find(String key) 
	{
		Record found=null;
		int i=0;
		int slot= baseHash(key)%stringArray.length;
		int step = stepHash(key)%stringArray.length;
		while (i<stringArray.length){
			slot= (slot + step)%stringArray.length;
			//if you find a null slot in the search, stop looking
			if (stringArray[slot]==(null)){
				return found;
			}
			else if(stringArray[slot].toHashValue==toHashKey(key)){
				//check the hash first, if equal, then check string
				if (stringArray[slot].key.equals(key)){
					found=stringArray[slot];
					return found;
				}
			}
			i++;
		} 
		return found;
	}



	public void doubleSize(){
		//copy over old table, double the size, then rehash into the new doubled table
		Record[] oldTable= new Record[stringArray.length];
		for( int i=0; i<stringArray.length; i++){
			oldTable[i]=stringArray[i];
		}
		stringArray = new Record[stringArray.length * 2];
		for(int i = 0; i < oldTable.length; i++) {
			if(oldTable[i] != null && !oldTable[i].key.equals("empty")) {
				insert(oldTable[i]);
			}
		}
	}



	///////////////////////////////////////////////////////////////////////


	// Convert a String key into an integer that serves as input to hash
	// functions.  This mapping is based on the idea of a linear-congruential
	// pesudorandom number generator, in which successive values r_i are 
	// generated by computing
	//    r_i = ( A * r_(i-1) + B ) mod M
	// A is a large prime number, while B is a small increment thrown in
	// so that we don't just compute successive powers of A mod M.
	//
	// We modify the above generator by perturbing each r_i, adding in
	// the ith character of the string and its offset, to alter the
	// pseudorandom sequence.
	//
	int toHashKey(String s)
	{
		int A = 1952786893;
		int B = 367257;
		int v = B;

		for (int j = 0; j < s.length(); j++)
		{
			char c = s.charAt(j);
			v = A * (v + (int) c + j) + B;
		}

		if (v < 0) v = -v;
		return v;
	}
	//two has functions, base and step, for use in double addressed open hashing.
	int baseHash(String s)
	{
		int sint=toHashKey(s);
		double hval= (Math.sqrt(5)-1)/2;
		return ((int)Math.floor(stringArray.length*((sint*hval)-Math.floor(sint*hval))));
	}
	int stepHash(String s)
	{
		int sint=toHashKey(s);
		double hval=(Math.sqrt(2)/2);
		int k= ((int)Math.floor(stringArray.length*((sint*hval)-Math.floor(sint*hval))));
		if (k%2==0){
			k=k+1;	
		}
		return k;
	}
	//toString method used for checking output--not part of lab.
	public String toString() {
		String rtn = "";
		for(int i = 0; i < stringArray.length; i++) {
			rtn += stringArray[i];
			rtn += ", ";
		}
		return rtn;
	}
}
