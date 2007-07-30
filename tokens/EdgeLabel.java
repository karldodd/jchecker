package tokens;

import java.util.*;

public interface EdgeLabel{
	String toString();
	String toLFString();
	String toFociString();

	//map is to save info: primitive var => var with suffix
	//revertMap is to save info: var with suffix => primitive var
	String toFociString(Map<String,String> map, Map<String,String> revertMap);
}
