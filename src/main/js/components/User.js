const React = require('react');
import { Redirect } from 'react-router';

/* User component (list element) */
export class User extends React.Component{

	constructor(props) {
		super(props);
	    this.state = {
	      error: null,
	      toList : null
	    };
	}
	
	
	deleteUser(id, e) {
	   e.preventDefault();
	   fetch("/deleteUser?id=" + id, {method: 'DELETE'})
	      .then(res => res.json())
	      .then((result) => {
	    	  this.setState({
	    		  toList: result.success,
	    		  error: result.error
	    	  });
	      	},
	    	(error) => {
	    	  this.setState({
	            error: true 
	          });
	      });
	}

	render() {
		const user = this.props.user;
		const user_id = user.id;
		if (this.state.toList) {
		      return (<Redirect to='/userlist' />)
		} else if (this.state.error) {
			return (
					<div>
	      				<tr className="text-center">
	      					<td>{user.id}</td>
		      				<td>{user.login}</td>
		      				<td>{user.name} {user.surname}</td>
		      				<td>{user.role}</td>
		      				<td>
		      					<button onClick={(e) => this.deleteUser(user_id, e)}>Delete user</button>
		      				</td>
		      			</tr>
		      			<tr className="text-center">
      						<td colspan="4">{this.state.error}</td>
      					</tr>
      				</div>		
			)
		}
		return (
      			<tr  className="text-center">
	      				<td>{user.id}</td>
	      				<td>{user.login}</td>
	      				<td>{user.name} {user.surname}</td>
	      				<td>{user.role}</td>
	      				<td>
	      					<button onClick={(e) => this.deleteUser(user_id, e)}>Delete user</button>
	      				</td>
      			</tr>
		)
	}
}