const React = require('react');
import { Redirect } from 'react-router';
import {IntlProvider, FormattedMessage } from "react-intl";
import messages from '../translation/messages';
import {Label} from './Label';

/* New user form component */
export class NewUser extends React.Component{
	  constructor() {
		    super();
		    this.state = {
		  	    error: null,
		  	    toList : null
	  	    };
		    this.handleSubmit = this.handleSubmit.bind(this);
	  }

	  handleSubmit(event) {
		    event.preventDefault();
		    const data = new FormData(event.target);
		    const newUser = {
		    		login: data.get('login') == null ? '' : data.get('login'),
    				name: data.get('name') == null ? '' : data.get('name'),
					surname: data.get('surname') == null ? '' : data.get('surname')
		    }
		    
		    
		    fetch("/saveUser", {method: 'POST', headers: {
		        	'content-type': 'application/json'
		      	},
		      	body: JSON.stringify(newUser)})
		      .then(res => res.json())
		      .then((result) => {
		    	  this.setState({
		    		  toList: result.success,
		    		  error: result.error
		    	  });
		      	},
		    	(error) => {
		    	  this.setState({
		            error: "Error while saving user" 
		          });
		    });
	  }
	
	  render() {
			if (this.state.toList) {
			    return (<Redirect to='/userlist' />)
			}    	  
		    return (
		    	<IntlProvider locale={window.current_language} messages={messages[window.current_language]}>
		    		<div className="container">
		    			<div className="col-12">
		    				<form onSubmit={this.handleSubmit}>
		    						<div className="form-group row">
		    							{ this.state.error
		    									? <span style={{color: 'red'}}>{this.state.error}</span>
		    									: null
		    							}
		    						</div>
		    					<div className="form-group row">
		    						<Label className="form-label col-3" htmlFor="login" messageId="newuser.login" defaultMessage="Enter user login" />
		    						<input id="login" name="login" type="text" className="form-control col-8" />	
		    					</div>
		    					<div className="form-group row">
		    						<Label className="form-label col-3" htmlFor="name" messageId="newuser.name" defaultMessage="Enter user name" />
		    						<input id="name" name="name" type="text" className="form-control col-8" />	
		    					</div>
		    					<div className="form-group row">
		    						<Label className="form-label col-3" htmlFor="surname" messageId="newuser.surname" defaultMessage="Enter user surname" />
		    						<input id="surname" name="surname" type="text" className="form-control col-8" />	
		    					</div>
		    					<button><FormattedMessage id="newuser.save" defaultMessage="Save user" /></button>
		    				</form>
		    			</div>
		    		</div>
		    	</IntlProvider>
		    );
	  }
	
}