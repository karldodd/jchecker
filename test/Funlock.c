 lock = 0; 
 b = 0;
// main() {
	if(b == 0) {
	  while( b < 4 ){
		   gotlock = 0;
		    if(b != 4 ) {
	           if(lock == 0) {lock = 1;}
	           else {ERROR;}
	           b = b + 1;
		    gotlock = gotlock + 1;
		   }
		  if(getlock ==1 ) {
		      if(lock == 1) {lock = 0;}
		      	 else {ERROR;}
		}
	 } 
	}
	while (ne!=old){
	  if(lock == 0) {lock = 1;}
	    else {ERROR;}
      old = ne;
	if(b == 0) {
	   if(lock == 1 ) {lock = 0;}
	   	 else	{ERROR;}
       ne = ne + 1;
	}
	} 
	  if(lock==1) {lock = 0;}
	  	else {ERROR;} 
//}


