<div class="card-date-picker text-center">
    <div>
    	<form method="POST">
    		<c:out value="${year}" />/
	        	<c:out value="${month}"/>/
	        	<c:out value="${day}"/>
        
	        <ul class="nav nav-pills flex-row">
		        <li class="nav-item">
	        		<input class="arrow left" type="submit" name="timeDirection" value="&lt">
	    		</li>
    		
	        	<c:set var = "frequency" scope ="session" value = "${frequency }"></c:set>
	        	

	        	<c:if test = "${frequency == 'all time'}">
		        	<li class="nav-item">
	                    <input class="nav-link active" type="submit" name="frequency" value="all time">
	                </li>
	                <li class="nav-item">
	                	<input class="nav-link" type="submit" name="frequency" value="daily">
	                </li>
	                <li class="nav-item">
	                	<input class="nav-link" type="submit" name="frequency" value="weekly">
	                </li>
	                <li class="nav-item">
	                    <input class="nav-link" type="submit" name="frequency" value="monthly">
	                </li>
	        	</c:if>
	        	<c:if test = "${frequency == 'daily'}">
		        	<li class="nav-item">
	                    <input class="nav-link" type="submit" name="frequency" value="all time">
	                </li>
	                <li class="nav-item">
	                	<input class="nav-link active" type="submit" name="frequency" value="daily">
	                </li>
	                <li class="nav-item">
	                	<input class="nav-link" type="submit" name="frequency" value="weekly">
	                </li>
	                <li class="nav-item">
	                    <input class="nav-link" type="submit" name="frequency" value="monthly">
	                </li>
	        	</c:if>
	        	<c:if test = "${frequency == 'weekly'}">
		        	<li class="nav-item">
	                    <input class="nav-link" type="submit" name="frequency" value="all time">
	                </li>
	                <li class="nav-item">
	                	<input class="nav-link" type="submit" name="frequency" value="daily">
	                </li>
	                <li class="nav-item">
	                	<input class="nav-link active" type="submit" name="frequency" value="weekly">
	                </li>
	                <li class="nav-item">
	                    <input class="nav-link" type="submit" name="frequency" value="monthly">
	                </li>
	        	</c:if>
	        	<c:if test = "${frequency == 'monthly'}">
		        	<li class="nav-item">
	                    <input class="nav-link" type="submit" name="frequency" value="all time">
	                </li>
	                <li class="nav-item">
	                	<input class="nav-link" type="submit" name="frequency" value="daily">
	                </li>
	                <li class="nav-item">
	                	<input class="nav-link" type="submit" name="frequency" value="weekly">
	                </li>
	                <li class="nav-item">
	                    <input class="nav-link active" type="submit" name="frequency" value="monthly">
	                </li>
	        	</c:if>
	        	<li class="nav-item">
	        		<input class="arrow right" type="submit" name="timeDirection" value="&gt">
	    		</li>
            </ul>
   		
   		
        	
        	
        </form>
    </div>
</div>
