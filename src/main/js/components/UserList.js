const React = require('react');

import {User} from './User';
import {IntlProvider, FormattedMessage } from "react-intl";
import messages from '../translation/messages';
import {TableHeader} from './TableHeader';

/* Users list */
export class UserList  extends React.Component{
	
	constructor(props) {
		super(props);
	    this.state = {
	      error: null,
	      isLoaded: false,
	      users: []
	    };
		
	}

	
	componentDidMount() {
		 fetch("/listUsers")
	      .then(res => res.json())
	      .then(
	        (result) => {
	          this.setState({
	            isLoaded: true,
	            users: result
	          });
	        },
	        // Note: it's important to handle errors here
	        // instead of a catch() block so that we don't swallow
	        // exceptions from actual bugs in components.
	        (error) => {
	          this.setState({
	            isLoaded: true,
	            error
	          });
	        }
	      )
	}
	
	render() {
		const {error, isLoaded, users} = this.state;
		if (error) {
		      return (
		    		<IntlProvider locale={window.current_language} messages={messages[window.current_language]}>
			  			<div className="form-group row justify-content-center align-items-center">
			  				<div className="col-lg-10 col-md-12 col-sm-12">
			  					<span><FormattedMessage id="userlist.error" defaultMessage="Error:" /> {error.message}</span>
			  				</div>
			  			</div>	
			  		</IntlProvider>	
		      )	
		} else if (!isLoaded) {
		      return (
		    		<IntlProvider locale={window.current_language} messages={messages[window.current_language]}>
			  			<div className="form-group row justify-content-center align-items-center">
			  				<div className="col-lg-10 col-md-12 col-sm-12">
			  					<span><FormattedMessage id="userlist.loading" defaultMessage="Loading..." /></span>
			  				</div>
			  			</div>	
		  			</IntlProvider>	
		      )	
		} else {
			const users_ = users.map(user =>
				<User key={user.id} user={user}/>
			);
			return (
					<IntlProvider locale={window.current_language} messages={messages[window.current_language]}>
						<table className="table table-striped table-bordered">
							<thead>
								<tr>
									<TableHeader className="text-center no-sort" text="Id" />
									<TableHeader className="text-center no-sort" 
											messageId="userlist.table.login" defaultMessage="Login"  />
									<TableHeader className="text-center no-sort" 
												messageId="userlist.table.name" defaultMessage="User name"  />
									<TableHeader className="text-center no-sort" 
													messageId="userlist.table.role" defaultMessage="User role"  />
									<TableHeader className="text-center no-sort" 
														messageId="userlist.table.actions" defaultMessage="Actions"  />
						    	</tr>
						    </thead>
							<tbody>
								{users_}
							</tbody>
						</table>
					</IntlProvider>	
				)
		}	
	}
}