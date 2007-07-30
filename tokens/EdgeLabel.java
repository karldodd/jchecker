package tokens;

import java.util.*;

public interface EdgeLabel{
	String toString();
	String toLFString();
	String toFociString();
	String toFociString(Map<String,String> map);
	Set<String> getPrimitiveVariableNames();
}
