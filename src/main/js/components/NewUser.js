const React = require('react');
import { Redirect } from 'react-router';
import {IntlProvider, FormattedMessage } from "react-intl";
import messages from '../translation/messages';

{/* СТраница добавления нового пользователя */}
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
		    						<label className="form-label col-3" htmlFor="login">
		    							<FormattedMessage id="newuser.login" defaultMessage="Enter user login" />
		    						</label>
		    						<input id="login" name="login" type="text" className="form-control col-8" />	
		    					</div>
		    					<div className="form-group row">
		    						<label className="form-label col-3" htmlFor="name">
		    							<FormattedMessage id="newuser.name" defaultMessage="Enter user name" />
		    						</label>
		    						<input id="name" name="name" type="text" className="form-control col-8" />	
		    					</div>
		    					<div className="form-group row">
		    						<label className="form-label col-3" htmlFor="surname">
		    							<FormattedMessage id="newuser.surname" defaultMessage="Enter user surname" />
		    						</label>
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