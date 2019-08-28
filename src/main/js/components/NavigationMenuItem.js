const React = require('react');
import {FormattedMessage} from "react-intl";
import {NavLink} from "react-router-dom";

/* Menu item */
export class NavigationMenuItem extends React.Component{

	constructor(props) {
		super(props);
	}
	
	
	render() {
		return (
				<NavLink to={this.props.link} >
					<FormattedMessage id={this.props.messageId} defaultMessage={this.props.defaultMessage} />
				</NavLink>
		)
	}
}