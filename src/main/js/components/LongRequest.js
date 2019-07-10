const React = require('react');
import messages from '../translation/messages';

/* Component for long request testing. Main idea: start long task and get it progress in one request */
export class LongRequest extends React.Component{
	  constructor() {
		    super();
		    this.state = {
		    	started: false,	
		  	    percentage: null
	  	    };
		    this.testRequest = this.testRequest.bind(this);
	  }

	  /* Обработчик нажатия на кнопку. Подписка на событие проверки состояния task-а */
	  testRequest(event) {
		  	const comp = this; 
		    event.preventDefault();
		    var evtSource = new EventSource("/longRequest");
		    evtSource.onmessage = function (event) {
		        var data = JSON.parse(event.data);
		        if (data.done) {
		        	evtSource.close();
		        	comp.setState({
			    	  started: false,
			    	  percentage: null
			    	});
		        }
		        else {
		        	if (data.percentage != comp.state.percentage) {
		        		comp.setState({
		        		  started: true,
		        		  percentage: data.percentage
		        	  });
		        	}
		        }
		    }
		    comp.setState({
    		  started: true,
    		  percentage: 0
    	  });
	  }
	
	  render() {
		    const {started, percentage} = this.state; 
		    return (
		    		<div className="container">
		    			<div className="col-12">
		    				<div className="row text-center">
		    					{ started
		    						? <input type="text" className="form-control" value={percentage + " %"} />
		    						: null
		    					}
		    				</div>
		    				<div className="row text-center">
	    						{ started
	    							? null
	    							: <button onClick={this.testRequest}>Test long request</button>
	    						}
	    					</div>
		    			</div>
		    		</div>
		    );
	  }
	
}